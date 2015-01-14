package com.storefront.linkcheck;

import java.io.*;
import java.util.*;

import com.adaptinet.adaptinetex.*;
import com.adaptinet.parser.*;

public class HTMLReader
    extends com.adaptinet.parser.BaseParser {

  protected Attributes attrs = new Attributes();
  protected HashMap namespaces = new HashMap();
  protected Object obj = null;
  protected DefaultHandler handler = null;
  protected DefaultHandler errorHandler = null;

  public HTMLReader() {
  }

  public void setContentHandler(DefaultHandler handler) {
    this.handler = handler;
  }

  public void setErrorHandler(DefaultHandler errorHandler) {
    this.errorHandler = errorHandler;
  }

  public Object parse(InputSource in) throws ParserException {

    obj = null;
    if ( (is = in.getCharacterStream()) == null)
      is = new InputStreamReader(in.getByteStream());

    try {
      handler.startDocument();
      ch = is.read();
      while (ch != -1) {
        if (ch == '<') {
          skipWhiteSpace();
          ch = is.read();
          parseElement();
        }
        else {
          ch = is.read();
        }
      }
      handler.endDocument();
      obj = handler.getObject();
    }
    catch (IndexOutOfBoundsException e) {
      if (errorHandler != null)
        errorHandler.error(new ParserException(e.getMessage()));
      else
        handler.error(new ParserException(e.getMessage()));
    }
    catch (Exception e) {
      if (errorHandler != null)
        errorHandler.error(new ParserException(e.getMessage()));
      else
        handler.error(new ParserException(e.getMessage()));
    }
    return obj;
  }

  protected final void parseElement() throws Exception {

    String name = null;
    String qname = null;
    String tag = null;
    String raw = null;
    String value = null;

    attrs.clear();
    if (ch == '/') {
      ch = is.read();
      qname = getWord();
      nSize = 0;
      skipUntil('>');
      handler.endElement(null, name, qname);
    }
    else {
      qname = getWord();
      nSize = 0;
      skipWhiteSpace();

      if (ch != -1 && ch != '>' && ch != '/') {
        do {
          append( (char) ch);
          ch = is.read();
          if (ch == '=') {
            raw = getBuffer();
            nSize = 0;
            skipUntil('"');
            ch = is.read();

            while (ch != -1 && ch != '>') {
              if (ch == '"') {
                ch = is.read();
                value = getBuffer();

                attrs.addAttribute(null, tag, raw.toLowerCase(), "", value);
                nSize = 0;
                break;
              }
              append( (char) ch);
              ch = is.read();
            }
            skipWhiteSpace();
          }
        }
        while (ch != -1 && ch != '>' && ch != '/');
      }

      handler.startElement(null, name, qname, attrs);

      if (ch == '/') {
        skipUntil('>');
        handler.endElement(null, name, qname);
      }
    }

    boolean bScript = false;
    if (qname != null)
      bScript = qname.equalsIgnoreCase("SCRIPT");
    nSize = 0;
    boolean inquote = false;
    if (ch == '>') {
      ch = is.read();
      while (ch != -1 && ! (ch == '<' && inquote == false)) {
        append( (char) ch);
        ch = is.read();
        if (bScript) {
          if (ch == '\'')
            inquote = inquote ? false : true;
        }
      }
    }

    if (nSize > 0) {
      handler.characters(buffer, 0, nSize);
    }
  }

  static public String normalize(char chars[], int start, int len) throws java.
      io.UnsupportedEncodingException {

    int j = start;
    for (int i = 0; i < len; i++, j++) {
      if (chars[i] == '&') {
        if (chars[i + 1] == 'l' && chars[i + 2] == 't' && chars[i + 3] == ';') {
          chars[j] = '<';
          i += 3;
        }
        else if (chars[i + 1] == 'g' && chars[i + 2] == 't' &&
                 chars[i + 3] == ';') {
          chars[j] = '>';
          i += 3;
        }
        else if (chars[i + 1] == 'a' && chars[i + 2] == 'm' &&
                 chars[i + 3] == 'p' && chars[i + 4] == ';') {
          chars[j] = '&';
          i += 4;
        }
        else if (chars[i + 1] == 'q' && chars[i + 2] == 'u' &&
                 chars[i + 3] == 'o' && chars[i + 4] == 't' &&
                 chars[i + 5] == ';') {
          chars[j] = '"';
          i += 5;
        }
        else if ( (chars[i + 1] == '#' && chars[i + 3] == ';') &&
                 (chars[i + 2] == '\r' || chars[i + 2] == '\n')) {
          chars[j] = chars[i + 2];
          i += 3;
        }
        else {
          chars[j] = chars[i];
        }
      }
      else {
        chars[j] = chars[i];
      }
    }
    return new String(chars, 0, j);
  }

  static public boolean isPI(byte chars[]) {
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '?')
        return true;
      else if (chars[i] != '<' && chars[i] != ' ')
        break;
    }
    return false;
  }
}
