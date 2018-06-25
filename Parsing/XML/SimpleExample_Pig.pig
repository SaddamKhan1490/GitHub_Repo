-- Input XML
-- --------------------------------------------------------------------------------------------------------------------------------------
<CATALOG>
<BOOK>
<TITLE>First Book Title</TITLE>
<AUTHOR>First Author Name</AUTHOR>
<COUNTRY>First Country Name</COUNTRY>
<COMPANY>First Company Name</COMPANY>
<PRICE>First Book Price</PRICE>
<YEAR>First Book Publication Year</YEAR>
</BOOK>
<BOOK>
<TITLE>Second Book Title</TITLE>
<AUTHOR>Second Author Name</AUTHOR>
<COUNTRY>Second Country Name</COUNTRY>
<COMPANY>Second Company Name</COMPANY>
<PRICE>Second Book Price</PRICE>
<YEAR>Second Book Publication Year</YEAR>
</BOOK>
<BOOK>
<TITLE>Third Book Title</TITLE>
<AUTHOR>Third Author Name</AUTHOR>
<COUNTRY>Third Country Name</COUNTRY>
<COMPANY>Third Company Name</COMPANY>
<PRICE>Third Book Price</PRICE>
<YEAR>Third Book Publication Year</YEAR>
</BOOK>
</CATALOG>
-- --------------------------------------------------------------------------------------------------------------------------------------

-- 1> Parsing using Regular Expression : Here using the XMLLoader() in piggy bank UDF to load the xml, so ensure that Piggy Bank UDF is registered.  Then I am using regular expression to parse the XML.

	 REGISTER piggybank.jar
	 A =  LOAD ‘/user/test/books.xml’ using org.apache.pig.piggybank.storage.XMLLoader(‘BOOK’) as (x:chararray);
	 B = foreach A GENERATE FLATTEN(REGEX_EXTRACT_ALL(x,‘<BOOK>\\s*<TITLE>(.*)</TITLE>\\s*<AUTHOR>(.*)</AUTHOR>\\s*<COUNTRY>(.*)</COUNTRY>\\s*<COMPANY>(.*)</COMPANY>\\s*<PRICE>(.*)</PRICE>\\s*<YEAR>(.*)</YEAR>\\s*</BOOK>’));
	 dump B;
 
-- Output : Once you will run this pig script then you will see the following output on your console.
	(First Book Title,First Author Name,First Country Name,First Company Name,First Book Price,First Book Publication Year)
	(Second Book Title,Second Author Name,Second Country Name,Second Company Name,Second Book Price,Second Book Publication Year)
	(Third Book Title,Third Author Name,Third Country Name,Third Company Name,Third Book Price,Third Book Publication Year)

-- 2> Parsing using XPath : It is second approach to solve xml parsing problem through Pig. XPath is a function that allows text extraction from xml. Starting PIG 0.13 , Piggy bank UDF comes with XPath support. It eases the XML parsing in PIG scripts.

	REGISTER piggybank.jar
	DEFINE XPath org.apache.pig.piggybank.evaluation.xml.XPath();
	A =  LOAD ‘/user/test/books.xml’ using org.apache.pig.piggybank.storage.XMLLoader(‘BOOK’) as (x:chararray);
	B = FOREACH A GENERATE XPath(x, ‘BOOK/AUTHOR’), XPath(x, ‘BOOK/PRICE’);
	dump B;

-- Output : Once you will run this pig script then you will see the following output on your console.
	(First Author Name,First Book Price)
	(Second Author Name,Second Book Price)
	(Third Author Name,Third Book Price)
