package com.example.jee;

import android.app.Activity;
import android.os.Bundle;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import static java.lang.String.format;

public class PdfViewer extends Activity implements OnPageChangeListener {
	public static String PDF_NAME = "com.example.jee.PDF_NAME";
    private PDFView pdfView;
    private String pdfName = "";
    private Integer pageNumber = 1;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    	pdfName = getIntent().getStringExtra(PDF_NAME);
    	setContentView(R.layout.activity_pdfviewer);
    	pdfView = (PDFView) findViewById(R.id.pdfView);
    	display(pdfName, true);
    	super.onCreate(savedInstanceState);
	}
	void afterViews() {
        display(pdfName, false);
    }
    private void display(String assetFileName, boolean jumpToFirstPage) {
        if (jumpToFirstPage) pageNumber = 1;
        setTitle(pdfName = assetFileName);

        pdfView.fromAsset(assetFileName)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(format("%s    %s / %s", pdfName, page, pageCount));
    }

    private boolean displaying(String fileName) {
        return fileName.equals(pdfName);
    }
}