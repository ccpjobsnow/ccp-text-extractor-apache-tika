package com.ccp.implementations.text.extractor.apache.tika;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.ccp.decorators.CcpTextDecorator;
import com.ccp.dependency.injection.CcpImplementation;
import com.ccp.especifications.text.extractor.CcpTextExtractor;

@CcpImplementation
public class CcpTextExtractorApacheTika implements CcpTextExtractor {

	@Override
	public String extractText(String content) {
		String[] split = content.split(",");
		String str = split[0];

		if (split.length > 1) {
			str = split[1];
		}

		CcpTextDecorator ccpTextDecorator = new CcpTextDecorator(str);
		InputStream is = ccpTextDecorator.getByteArrayInputStream();
		
		ParseContext context = new ParseContext();
		Detector detector = new DefaultDetector();
		Parser parser = new AutoDetectParser(detector);
		context.set(Parser.class, parser);
		
		Metadata metadata = new Metadata();
		try(OutputStream os = new ByteArrayOutputStream();) {
			ContentHandler handler = new BodyContentHandler(os);
			parser.parse(is, handler, metadata, context);
			String extractedText = os.toString();
			return extractedText;
		} catch (IOException | SAXException | TikaException e) {
			throw new RuntimeException(e);
		}

	}

	


}
