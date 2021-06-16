package myApp;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.opencsv.CSVWriter;

//import org.omg.CORBA.NameValuePair;
import org.apache.http.NameValuePair;

import dao.PeopleDao;

import javax.swing.JTable;

public class PeopleClient extends JFrame implements ActionListener{
	
	private JTable table;
	private JTextField idField;
	private JTextField fnameField;
	private JTextField lnameField;
	private JTextField carField;
	private JTextField cityField;
	private JTextField countryField;
	private JButton deleteButton;
	private JButton getAllButton;
	private JButton getButton;
	private JButton putButton;
	private JButton postButton;
	private JButton exportButton;
	String col[] = {"ID", "First Name", "Last Name", "Car", "City", "Country"};
	DefaultTableModel model = new DefaultTableModel(col, 0);
	private JTextField getField;
	public PeopleClient() {
		super("Gui");
		getContentPane().setLayout(null);
		
		
		table = new JTable(model);
		table.setBounds(334, 5, 376, 364);
		getContentPane().add(table);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(0, 0, 60, 25);
		getContentPane().add(lblNewLabel);
		
		idField = new JTextField();
		idField.setBounds(114, 2, 158, 20);
		getContentPane().add(idField);
		idField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("First Name");
		lblNewLabel_1.setBounds(0, 36, 60, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Last Name");
		lblNewLabel_2.setBounds(0, 61, 60, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Car");
		lblNewLabel_3.setBounds(0, 86, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("City");
		lblNewLabel_4.setBounds(0, 111, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Country");
		lblNewLabel_5.setBounds(0, 136, 46, 14);
		getContentPane().add(lblNewLabel_5);
		
		fnameField = new JTextField();
		fnameField.setBounds(114, 33, 158, 20);
		getContentPane().add(fnameField);
		fnameField.setColumns(10);
		
		lnameField = new JTextField();
		lnameField.setBounds(114, 58, 158, 20);
		getContentPane().add(lnameField);
		lnameField.setColumns(10);
		
		carField = new JTextField();
		carField.setBounds(114, 83, 158, 20);
		getContentPane().add(carField);
		carField.setColumns(10);
		
		cityField = new JTextField();
		cityField.setBounds(114, 108, 158, 20);
		getContentPane().add(cityField);
		cityField.setColumns(10);
		
		countryField = new JTextField();
		countryField.setBounds(114, 133, 158, 20);
		getContentPane().add(countryField);
		countryField.setColumns(10);
		
		getAllButton = new JButton("GET All");
		getAllButton.setBounds(0, 193, 89, 23);
		getContentPane().add(getAllButton);
		getAllButton.addActionListener(this);
		
		getButton = new JButton("GET ID");
		getButton.setBounds(115, 193, 89, 23);
		getContentPane().add(getButton);
		getButton.addActionListener(this);
		
		putButton = new JButton("PUT");
		putButton.setBounds(0, 227, 89, 23);
		getContentPane().add(putButton);
		putButton.addActionListener(this);
		
		postButton = new JButton("POST");
		postButton.setBounds(115, 227, 89, 23);
		getContentPane().add(postButton);
		postButton.addActionListener(this);
		
		deleteButton = new JButton("DELETE");
		deleteButton.setBounds(0, 261, 89, 23);
		getContentPane().add(deleteButton);
		deleteButton.addActionListener(this);
		
		exportButton = new JButton("EXPORT");
		exportButton.setBounds(115, 261, 89, 23);
		getContentPane().add(exportButton);
		
		getField = new JTextField();
		getField.setBounds(0, 386, 710, 20);
		getContentPane().add(getField);
		getField.setColumns(10);
		exportButton.addActionListener(this);
//		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
	}
	
	public static void main(String [] args) {
		System.out.println("Hello World");
		PeopleClient window = new PeopleClient();
		window.setSize(900,450);
		window.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(deleteButton)) {
				System.out.println("Delete button");
				try {
					URI uri = new URIBuilder()
							.setScheme("http")
							.setHost("localhost")
							.setPort(8080)
							.setPath("/DistributedSystem/rest/people/"+idField.getText())
							.build();
					System.out.println(uri.toString());
					HttpDelete httpDelete = new HttpDelete(uri);
					httpDelete.setHeader("Accept", "text/html");
					CloseableHttpClient client = HttpClients.createDefault();
					System.out.println("Sending DELETE request.....");
					CloseableHttpResponse response = client.execute(httpDelete);
					System.out.println("Response: "+ response.toString());
					getData();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		if(e.getSource().equals(getAllButton)) {
			System.out.println("Get All Button");
			getData();
		}
		if(e.getSource().equals(getButton)) {
			System.out.println("Get Button");
			CloseableHttpResponse response = null;
			URI uri;
			try {
				uri = new URIBuilder()
						.setScheme("http")
						.setHost("localhost")
						.setPort(8080)
						.setPath("/DistributedSystem/rest/people/"+idField.getText()).build();
				System.out.println(uri.toString());
				
				HttpGet httpGet = new HttpGet(uri);
				httpGet.setHeader("Accept", "application/xml");
				
				CloseableHttpClient httpClient = HttpClients.createDefault();
				response = httpClient.execute(httpGet);
				
				HttpEntity entity = response.getEntity();
				String text = EntityUtils.toString(entity);
				System.out.println(text);
				
				Person person = new ParsePerson().doParsePerson(text);
				System.out.println(person.toString());
				getField.setText(person.toString());
				
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource().equals(putButton)) {
			System.out.println("Get Button");
			URI uri;
			try {
				uri = new URIBuilder()
						.setScheme("http")
						.setHost("localhost")
						.setPort(8080)
						.setPath("/DistributedSystem/rest/people/"+idField.getText())
						.build();
				System.out.println(uri.toString());
				
				HttpPut httpPut = new HttpPut(uri);
				httpPut.setHeader("Accept", "text/html");
				CloseableHttpClient client = HttpClients.createDefault();
				
				CloseableHttpClient httpClient = HttpClients.createDefault();
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("id", idField.getText()));
				nameValuePairs.add(new BasicNameValuePair("firstName", fnameField.getText()));
				nameValuePairs.add(new BasicNameValuePair("lastName", lnameField.getText()));
				nameValuePairs.add(new BasicNameValuePair("car", carField.getText()));
				nameValuePairs.add(new BasicNameValuePair("city", cityField.getText()));
				nameValuePairs.add(new BasicNameValuePair("country", countryField.getText()));
				
				httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				System.out.println("Sending PUT request....");
				CloseableHttpResponse response = client.execute(httpPut);
				System.out.println("Response: " + response.toString());
				getData();
				
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource().equals(postButton)) {
			System.out.println("Get Button");
			URI uri;
			try {
				uri = new URIBuilder()
						.setScheme("http")
						.setHost("localhost")
						.setPort(8080)
						.setPath("/DistributedSystem/rest/people")
						.build();
				System.out.println(uri.toString());
				
				HttpPost httpPost = new HttpPost(uri);
				httpPost.setHeader("Accept", "text/html");
				CloseableHttpClient client = HttpClients.createDefault();
				
				CloseableHttpClient httpClient = HttpClients.createDefault();
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("firstName", fnameField.getText()));
				nameValuePairs.add(new BasicNameValuePair("lastName", lnameField.getText()));
				nameValuePairs.add(new BasicNameValuePair("car", carField.getText()));
				nameValuePairs.add(new BasicNameValuePair("city", cityField.getText()));
				nameValuePairs.add(new BasicNameValuePair("country", countryField.getText()));
				
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				System.out.println("Sending PUT request....");
				CloseableHttpResponse response = client.execute(httpPost);
				System.out.println("Response: " + response.toString());
				getData();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource().equals(exportButton)) {
			System.out.println("ExportButton");
			exportData();
		}
	}
	
	private void fillTable(List People) {
		model.setRowCount(0);
		ArrayList people = PeopleDao.instance.showAll();
		Object rowData[] = new Object[6];
		rowData[0] = "ID";
		rowData[1] = "First Name";
		rowData[2] = "Last Name";
		rowData[3] = "Car";
		rowData[4] = "City";
		rowData[5] = "Country";
		model.addRow(rowData);
		
		for(int i = 0; i < people.size(); i++) {
			rowData[0] = ((Person) people.get(i)).getId();
			rowData[1] = ((Person) people.get(i)).getFirstName();
			rowData[2] = ((Person) people.get(i)).getLastName();
			rowData[3] = ((Person) people.get(i)).getCar();
			rowData[4] = ((Person) people.get(i)).getCity();
			rowData[5] = ((Person) people.get(i)).getCountry();
			//System.out.println(rowData[1]);
			model.addRow(rowData);
		}
	}
	private void getData() {
		CloseableHttpResponse response = null;
		URI uri;
		try {
			uri = new URIBuilder()
					.setScheme("http")
					.setHost("localhost")
					.setPort(8080)
					.setPath("/DistributedSystem/rest/people").build();
			System.out.println(uri.toString());
			
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setHeader("Accept", "application/xml");
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpGet);
			
			HttpEntity entity = response.getEntity();
			String text = EntityUtils.toString(entity);
			System.out.println(text);
			
			List<Person> personList = new ParsePeople().doParsePeople(text);
			for(Person person : personList) {
				System.out.println("First Name: " + person.getFirstName());
			}
			fillTable(personList);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	private void exportData() {
		CloseableHttpResponse response = null;
		URI uri;
		try {
			uri = new URIBuilder()
					.setScheme("http")
					.setHost("localhost")
					.setPort(8080)
					.setPath("/DistributedSystem/rest/people").build();
			System.out.println(uri.toString());
			
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setHeader("Accept", "application/xml");
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpGet);
			
			HttpEntity entity = response.getEntity();
			String text = EntityUtils.toString(entity);
			System.out.println(text);
			
			List<Person> personList = new ParsePeople().doParsePeople(text);
			for(Person person : personList) {
				System.out.println("First Name: " + person.getFirstName());
			}
			writeToFile(personList);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	private void writeToFile(List people){
		try{
			System.out.println("In writeToFile");
			try {
				
				FileWriter outputFile = new FileWriter("data.csv", false);
				CSVWriter writer = new CSVWriter(outputFile);
				String[] header = {"ID", "First Name", "Last Name", "Car", "City", "Country"};
				writer.writeNext(header);
				String[] data = {};
				for(int i = 0; i < people.size(); i++) {
					String ID = ((Person) people.get(i)).getId();
					String fname = ((Person) people.get(i)).getFirstName();
					String lname = ((Person) people.get(i)).getLastName();
					String car = ((Person) people.get(i)).getCar();
					String city = ((Person) people.get(i)).getCity();
					String country = ((Person) people.get(i)).getCountry();
					//System.out.println(rowData[1]);
					String[] data1 = {ID, fname, lname, car, city, country};
					writer.writeNext(data1);
				}
				writer.close();
				//outputFile.close();
			}
			catch(Exception e) {e.printStackTrace();}
			
		}
		catch(Exception e){e.printStackTrace();}
	}
}

