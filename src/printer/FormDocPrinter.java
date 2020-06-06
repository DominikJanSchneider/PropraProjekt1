package printer;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import org.apache.pdfbox.printing.PDFPrintable;


public class FormDocPrinter
{
	public void print(PrintData data) throws PrinterException, IOException
	{
		FormDoc document = new FormDoc(data);

		PrinterJob pJob = PrinterJob.getPrinterJob();
		pJob.setPrintable(new PDFPrintable(document));
		if(pJob.printDialog())
		{
			pJob.print();
		}	
		document.close();
	}
}
