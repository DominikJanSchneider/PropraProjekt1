package printer;

import java.io.IOException;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

public class FormDoc extends PDDocument 
{
	private PrintData data;
	private final int textSX;
	private final int textEX;
	private final int textSY;
	private final int textEY;
	private int textY;
	private int headerLineDistance;
	private int lineDistance;
	private int labelGap;
	private PDPageContentStream contentStream;
	private PDPage currPage;
	private PDFont font;
	private PDAcroForm form;
	private final PDRectangle paperFormat;
	private final float pageHeight;
	private final float pageWidth;
	
	FormDoc(PrintData data) throws IOException
	{
		super();
		this.data = data;
		paperFormat = PDRectangle.A4;
		currPage = new PDPage(paperFormat);
		pageWidth = currPage.getMediaBox().getWidth();
		pageHeight = currPage.getMediaBox().getHeight();
		addPage(currPage);
		textSX = 100;
		textEX = (int)(pageWidth - 100);
		textSY = 750;
		textEY = 50;
		textY = textSY;
		labelGap = 100;
		headerLineDistance = 30;
		lineDistance = 20;
		font = PDType1Font.HELVETICA_BOLD;
		contentStream = new PDPageContentStream(this, currPage);
		
		
		
		//form setup
		form = new PDAcroForm(this);
        getDocumentCatalog().setAcroForm(form);
        PDResources resources = new PDResources();
        resources.put(COSName.getPDFName("Helv"), font);
        form.setDefaultResources(resources);
        
		
		//text field descriptions
		contentStream.beginText();
		contentStream.setFont( font, 20 );
		contentStream.newLineAtOffset(textSX, textY);
		contentStream.showText("Sicherheitsunterweisung");
		contentStream.newLineAtOffset(0, -headerLineDistance);
		contentStream.setFont(font, 12);
		contentStream.showText("Name:");
		contentStream.newLineAtOffset(0, -lineDistance);
		contentStream.showText("Vorname:");
		contentStream.newLineAtOffset(0, -lineDistance);
		contentStream.showText("Datum:");
		contentStream.newLineAtOffset(0, -lineDistance);
		contentStream.showText("Ifwt:");
		contentStream.newLineAtOffset(0, -lineDistance);
		contentStream.showText("MNaF:");
		contentStream.newLineAtOffset(0, -lineDistance);
		contentStream.showText("Intern:");
		contentStream.newLineAtOffset(0, -lineDistance);
		contentStream.showText("Extern:");
		contentStream.endText();

        //general text fields 
        String familyName = data.getFamilyName();
        String firstName = data.getFirstName();
        String date = data.getDate();
        String ifwt = data.getIfwt();
        String mnaf = data.getMNaF();
        String intern = data.getIntern();
        String extern = data.getExtern();
	        
	     String[] values = 
	     {
	    		 familyName,
	    		 firstName,
	    		 date,
	    		 ifwt,
	    		 mnaf,
	    		 intern,
	    		 extern
	     };
        
        textY -= headerLineDistance;
        for(int i = 0; i < values.length; i++)
        {
        	createTextField(values[i], textSX + labelGap, textY - 5, textEX - (textSX + labelGap), 15, false);
        	textY -= lineDistance;
        }
        
        //creating of instruction info text area blocks
        
        String genInstr = data.getGeneralInstructions();
        String labSetup = data.getLabSetup();
        String dangerSubst = data.getDangerousSubstances();
        
        int textAreaBlockWidth = textEX - textSX;
        createTextAreaBlock("Allgemeine Unterweisung", genInstr, textAreaBlockWidth);
        createTextAreaBlock("Laboreinrichtungen", labSetup, textAreaBlockWidth);
        createTextAreaBlock("Gefahrstoffe", dangerSubst, textAreaBlockWidth);
        
		//signature
		contentStream.moveTo(textSX, textEY + 50);
		contentStream.lineTo(300, textEY + 50);
		contentStream.stroke();
		contentStream.beginText();
		contentStream.newLineAtOffset(textSX, textEY + 40);
		contentStream.setFont(font, 8);
		contentStream.showText("Datum, Unterschrift");
		contentStream.endText();
		
		//header and footer
		for(int i = 0; i < this.getNumberOfPages(); i++)
		{
			contentStream.close();
			contentStream = new PDPageContentStream(this, this.getPage(i), PDPageContentStream.AppendMode.APPEND, false, false);
			contentStream.setFont(font, 8);
			contentStream.beginText();
			contentStream.newLineAtOffset(50, 800);
			contentStream.showText("Sicherheitsunterweisung: " + data.getFamilyName() + ", " + data.getFirstName());
			contentStream.endText();
			contentStream.beginText();
			contentStream.newLineAtOffset(470, textEY);
			contentStream.showText((i + 1) + " von " + this.getNumberOfPages());
			contentStream.endText();
		}
		
		
		// Makes sure that the content stream is closed:
		contentStream.close();
	}
	
	private void createTextAreaBlock(String title, String value, int fieldWidth) throws IOException
	{
		int charWidth = 9;
		int charCount = fieldWidth/charWidth;
		String text = value;
		StringBuffer strBuffer;
        String[] lines = value.split("\n");
        for(int i = 0; i < lines.length; i++)
        {
        	if(lines[i].length() > charCount)
        	{
        		strBuffer = new StringBuffer(lines[i]);
        		int j = charCount - 1;
        		for(; strBuffer.charAt(j) != ' '; j--)
        		{
        			if(j == 0)
        			{
        				strBuffer = strBuffer.insert(charCount - 1, "\n");
        				break;
        			}
        		}
        		if(j != 0)
        		{
        			strBuffer = strBuffer.replace(j, j + 1, "\n");
        		}
        		lines[i] = strBuffer.toString();
        		text = linesToString(lines, 0, lines.length - 1);
        		lines = text.split("\n");
        	}
        }
        int lineCount = lines.length;
    	final int fieldLineHeight = 14;
    	final int extra = 22;
    	int fieldHeight = lineCount * fieldLineHeight + extra;
    	while(lineCount != 0)
    	{
    		int rest = textY - 2 * lineDistance - (textEY + 70);
    		if( rest >= fieldHeight)
        	{
        		textY -= lineDistance;
        		showTextAreaFieldTitle(title);
        		textY -= lineDistance + fieldHeight;
        		createTextField(text, textSX, textY, fieldWidth, fieldHeight, true);
        		break;
        	}
        	else if(rest >= fieldLineHeight * 3 + extra)
        	{
        		lines = createTextBlockPart(title, lines, fieldWidth, fieldLineHeight, extra, rest);
        		text = linesToString(lines, 0, lines.length - 1);
            	createNewPageAndSetFocus();
            	lineCount = lines.length;
            	fieldHeight = fieldLineHeight * lineCount + extra;
        	}
        	else
        	{
        		createNewPageAndSetFocus();
        		rest = textY - 2 * lineDistance - (textEY + 70);
        		lines = createTextBlockPart(title, lines, fieldWidth, fieldLineHeight, extra, rest);
        		text = linesToString(lines, 0, lines.length - 1);
        		lineCount = lines.length;
        		fieldHeight = fieldLineHeight * lineCount + extra;
        	}
    	}
	}
	
	private void createTextField(String value , int x, int y, int width, int height, boolean multiline) throws IOException
	{
		PDTextField textField = new PDTextField(form);
		String defaultAppearance = "/Helv 12 Tf 0 0 0 rg";
        textField.setDefaultAppearance(defaultAppearance);
        textField.setMultiline(multiline);
    	form.getFields().add(textField);
    	PDAnnotationWidget widget = textField.getWidgets().get(0);
    	
    	PDRectangle rect = new PDRectangle(x, y, width, height);
    	widget.setRectangle(rect);
        widget.setPage(currPage);
        PDAppearanceCharacteristicsDictionary fieldAppearance = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
        fieldAppearance.setBorderColour(new PDColor(new float[]{0,0,0}, PDDeviceRGB.INSTANCE));
        widget.setAppearanceCharacteristics(fieldAppearance);
        widget.setPrinted(true);
        currPage.getAnnotations().add(widget);
    	
        textField.setValue(value);
	}
	
	private void showTextAreaFieldTitle(String title) throws IOException
	{
		contentStream.beginText();
    	contentStream.newLineAtOffset(textSX, textY);
		contentStream.setFont(font, 12);
		contentStream.showText(title);
    	contentStream.endText();
	}

	private void createNewPageAndSetFocus() throws IOException
	{
		currPage = new PDPage(paperFormat);
		addPage(currPage);
		contentStream.close();
		contentStream = new PDPageContentStream(this, currPage);
		textY = textSY;
	}
	
	private String[] createTextBlockPart(String title, String[] lines, int fieldWidth, int fieldLineHeight, int extra, int rest) throws IOException
	{
		int lineCount = rest/fieldLineHeight;
		if(lines.length <= lineCount)
		{
			lineCount = lines.length;
		}
		textY -= lineDistance;
		showTextAreaFieldTitle(title);
		textY -= lineDistance + lineCount * fieldLineHeight + extra;
		String text = linesToString(lines, 0, lineCount - 1);
		createTextField(text, textSX, textY, fieldWidth, lineCount * fieldLineHeight + extra, true);
		
		String[] restText = new String[lines.length - lineCount];
		
		for(int j = 0, k = lineCount; k < lines.length; j++, k++)
		{
			restText[j] = lines[k];
		}
		return restText;
	}
	
	private String linesToString(String[] lines, int begin, int end)
	{
		String res = "";
		for(int i = begin; i <= end; i++)
		{
			
			if(i + 1 == lines.length)
			{
				res = res.concat(lines[i]);
			}
			else
			{
				res = res.concat(lines[i] + "\n");
			}
		}
		return res;
	}
}
