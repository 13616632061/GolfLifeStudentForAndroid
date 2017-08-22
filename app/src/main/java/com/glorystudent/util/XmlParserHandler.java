package com.glorystudent.util;



import com.glorystudent.entity.ItemModle;
import com.glorystudent.entity.OptionModle;
import com.glorystudent.entity.TestModle;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlParserHandler extends DefaultHandler {

	/**
	 * 存储所有的解析对象
	 */
	private List<TestModle> testlist = new ArrayList<TestModle>();

	public XmlParserHandler() {

	}

	public List<TestModle> getDataList() {
		return testlist;
	}

	@Override
	public void startDocument() throws SAXException {
		// 当读到第一个开始标签的时候，会触发这个方法
	}

	TestModle testModle = new TestModle();
	OptionModle optionModle = new OptionModle();
	ItemModle itemModle = new ItemModle();

	@Override
	public void startElement(String uri, String localName, String qName,
							 Attributes attributes) throws SAXException {
		// 当遇到开始标记的时候，调用这个方法
		if (qName.equals("test")) {
			testModle = new TestModle();
			testModle.setName(attributes.getValue(0));
			testModle.setList(new ArrayList<OptionModle>());
		} else if (qName.equals("option")) {
			optionModle = new OptionModle();
			optionModle.setName(attributes.getValue(0));
			optionModle.setField(attributes.getValue(1));
			optionModle.setList(new ArrayList<ItemModle>());
		} else if (qName.equals("item")) {
			itemModle = new ItemModle();
			itemModle.setName(attributes.getValue(0));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// 遇到结束标记的时候，会调用这个方法
		if (qName.equals("item")) {
			optionModle.getList().add(itemModle);
		} else if (qName.equals("option")) {
			testModle.getList().add(optionModle);
		} else if (qName.equals("test")) {
			testlist.add(testModle);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}
}
