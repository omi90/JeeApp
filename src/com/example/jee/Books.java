package com.example.jee;

import com.example.jee.R;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.books.bean.BooksBean;

public class Books extends Activity implements OnNavigationListener {
	 
	ActionBar actionBar;
	ArrayList<BooksBean> phyBook,matBook,aptiBook,chemBook;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_books);
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		ArrayList<String> dropDown = new ArrayList<String>();
		dropDown.add("Physics Books");
		dropDown.add("Mathematics Books");
		dropDown.add("Chemistry Books");
		dropDown.add("Aptitude Books");
		
		ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_books, R.id.item, dropDown);
		actionBar.setListNavigationCallbacks(adapter, this);
		
		
		
		try {
			 phyBook =xmlParser(1);
			 matBook =xmlParser(2);
			 chemBook =xmlParser(3);
			 aptiBook =xmlParser(4);
			 
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	listView= (ListView)findViewById(R.id.listview);
		
		arrAdap adap =new arrAdap(getApplicationContext(),R.layout.books_list_view , 0, phyBook);
		
		listView.setAdapter(adap);
	     
		
		//Facebook page link- https://www.facebook.com/jeemainnews

		//	Website link- http://www.jee-main.co.in/

		//	Gmail address- chiragsachdeva.24@gmail.com

		//	Application name- JEE Main 2015-2016		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.books, menu);
		return true;
	}
	
	private ArrayList<BooksBean> xmlParser(int cat) throws ParserConfigurationException, SAXException, IOException{
		
		ArrayList<BooksBean> arrList=new ArrayList<BooksBean>();
		
		InputStream filePath=getResources().openRawResource(R.raw.booksdetails);
		/*byte[] buffer = new byte[50];
		String str = "";
		while(filePath.read(buffer)>0){
			String sb = new String(buffer);
			buffer = new byte[50];
			str += sb;
		}
		System.out.println(str);*/
		DocumentBuilderFactory builderFactory=  DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=builderFactory.newDocumentBuilder();
		Document doc = builder.parse (new InputSource(filePath));
		//Document doc = builder.parse (str);
		
		doc.getDocumentElement ().normalize ();
		
		NodeList listofBooks = doc.getElementsByTagName("book");
		int noOfBooks= listofBooks.getLength();
		
		
		for(int i=0;i<noOfBooks;i++)
		{
			
			Node books= listofBooks.item(i);
			
			if(books.getNodeType()==Node.ELEMENT_NODE){
				
				BooksBean bean= new BooksBean();
				
				 Element firstPersonElement = (Element)books;
				 
				 NodeList bookImg = firstPersonElement.getElementsByTagName("bookImg");
                 Element firstNameElement = (Element)bookImg.item(0);

                 NodeList img = firstNameElement.getChildNodes();
                 bean.setBookImg(((Node)img.item(0)).getNodeValue().trim());
                 
                 //-------
                 
                 
                 NodeList bookCat = firstPersonElement.getElementsByTagName("bookCat");
                 Element lastNameElement1 = (Element)bookCat.item(0);

                 NodeList nameCat = lastNameElement1.getChildNodes();
                 bean.setBookCat(Integer.parseInt(((Node)nameCat.item(0)).getNodeValue()));

                 //----
                 
                 NodeList bookName = firstPersonElement.getElementsByTagName("bookName");
                 Element lastNameElement = (Element)bookName.item(0);

                 NodeList name = lastNameElement.getChildNodes();
                 bean.setBookName(((Node)name.item(0)).getNodeValue().trim());

                 //----
                 NodeList bookDesc = firstPersonElement.getElementsByTagName("bookDesc");
                 Element ageElement = (Element)bookDesc.item(0);

                 NodeList desc = ageElement.getChildNodes();
                 bean.setBookDesc(((Node)desc.item(0)).getNodeValue().trim());
 
                 
                 //---
                 NodeList bookBuy = firstPersonElement.getElementsByTagName("bookBuy");
                 Element buybook = (Element)bookBuy.item(0);

                 NodeList buy = buybook.getChildNodes();
                 bean.setBookBuy(((Node)buy.item(0)).getNodeValue().trim());
 
				if(bean.getBookCat()==cat)
                 arrList.add(bean);
			}
			
			
		}
		
		return arrList;
	}
	
	
	public class arrAdap extends ArrayAdapter<BooksBean>{

	private final	List<BooksBean> beans;
	private final	Context context1;
	//private final int caegory;
		
		public arrAdap(Context context, int resource, int textViewResourceId,
				List<BooksBean> objects) {
			super(context, resource, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
			beans=objects;
			context1=context;
			//caegory=cat;
			
		}
		
		
		public View getView(final int position, View convertView, ViewGroup parent) {
		    LayoutInflater inflater = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.books_list_view, parent, false);
		    
		    
		    
		    TextView textView = (TextView) rowView.findViewById(R.id.name_book);
		    textView.setText(beans.get(position).getBookName());
		    
		    TextView textView1 = (TextView) rowView.findViewById(R.id.desc_books);
		    textView1.setText(beans.get(position).getBookDesc());
		    
		    ImageView imageView = (ImageView) rowView.findViewById(R.id.books_image);
		    imageView.setImageResource(getResources().getIdentifier(beans.get(position).getBookImg(), "drawable",getPackageName()));
		 
		
		    
		    Button button=(Button)rowView.findViewById(R.id.buy_now);
		    button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Uri uri = Uri.parse(beans.get(position).getBookBuy());
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				}
			});

		    return rowView;
		  }
		
	}


	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		
		if(itemPosition==0){
         arrAdap adap =new arrAdap(getApplicationContext(),R.layout.books_list_view , 0, phyBook);
		
		 listView.setAdapter(adap);
		}
		else if(itemPosition==1){
	         arrAdap adap =new arrAdap(getApplicationContext(),R.layout.books_list_view , 0, matBook);
	 		
			 listView.setAdapter(adap);
			}
		else
			if(itemPosition==2){
		         arrAdap adap =new arrAdap(getApplicationContext(),R.layout.books_list_view , 0, chemBook);
				
				 listView.setAdapter(adap);
				}
			else
				{
			         arrAdap adap =new arrAdap(getApplicationContext(),R.layout.books_list_view , 0, aptiBook);
					
					 listView.setAdapter(adap);
					}
		return true;
	}
	
	
}