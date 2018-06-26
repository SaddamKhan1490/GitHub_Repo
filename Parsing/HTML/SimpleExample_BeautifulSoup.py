#########################################################################################################################################################################
#Developer    : Saddam                                                                                                                                                  #
#Date         : 2016/12/28                                                                                                                                              #
#Description  : This script will load the given web page i.e. input_url, and will parse and return all the URL's & their Title's which are mentioned on the given page  #
#########################################################################################################################################################################

# Import required packages
import requests
from bs4 import BeautifulSoup

# Load specified web page using BeautifulSoup & HTTP request
def html_parser(page,WebUrl):
    if(page>0):
        url = WebUrl
        code = requests.get(url)                                                    # Load entire content of input_url
        plain = code.text                                                           # Convert and store the content as text 
        soup = BeautifulSoup(plain, "html.parser")                                  # Parse the content using 'html.parser' of BeautifulSoup
        
        for link in soup.findAll('a', {'class':'s-access-detail-page'}):            # Extract all anchor tag i.e.'<a>', of input_url
            tet = link.get('title')                                                 # Extract 'title' part from already extracted anchor tag, similarly we can extract other HTML tags
            print(tet)
            tet_2 = link.get('href')                                                # Extract 'href' or link part from already extracted anchor tag, similarly we can extract other HTML tags
            print(tet_2)
 
# Input URL which needs to be crawled
input_url = 'http://www.amazon.in/'                                                 

# Call Web Crawler
html_parser(1,url)                                                                          

# Output: List of all URL's & there associated Title, which are available on the input_url page
