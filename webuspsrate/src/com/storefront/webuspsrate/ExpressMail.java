// Title:        ExpressMail
// Copyright:    Copyright (c) 2002
// Author:       XML-Schema Compiler
// Company:      Adaptinet, Inc.
// Description:  This class was generated by Adaptinet's XML-Schema Compiler
// Schema name:  C:\projects\storefront\webuspsrate\webuspsrate.xsd
// Java SDK:     1.2.2 or later

package com.storefront.webuspsrate;
import java.io.Serializable;

import java.util.*;

public class ExpressMail implements Serializable  {
	public ExpressMail() {
	}
	public ExpressMail(String _contentData) {
		 this._contentData=_contentData;
	}
	public String getContentData() {
		return _contentData;
	}
	public void setContentData(String newValue) {
		 _contentData = newValue;
	}
	private String _contentData;
}

