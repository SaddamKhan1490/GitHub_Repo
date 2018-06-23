###############################################################################################################################################################
#Developer    : Saddam                                                                                                                                        #
#Date         : 2016/10/22                                                                                                                                    #
#Description  : This script will crawl the given web page i.e. input_url, and will return all the URL's & their Title's which are mentioned on the given page #
###############################################################################################################################################################

# Import required packages
import requests
from bs4 import BeautifulSoup

# Create web crawler using BeautifulSoup & HTTP request
def web(page,WebUrl):
    if(page>0):
        url = WebUrl
        code = requests.get(url)                                                    # Load entire content of input_url
        plain = code.text                                                           # Convert and store the content as text 
        soup = BeautifulSoup(plain, "html.parser")                                  # Parse the content using 'html.parser' of BeautifulSoup
        
        for link in soup.findAll('a', {'class':'s-access-detail-page'}):            # Extract all anchor tag i.e.'<a>', of input_url
            tet = link.get('title')                                                 # Extract 'title' part from already extracted anchor tag
            print(tet)
            tet_2 = link.get('href')                                                # Extract 'href' or link part from already extracted anchor tag
            print(tet_2)
 
# Input URL which needs to be crawled
input_url = 'http://www.amazon.in/'                                                 

# Call Web Crawler
web(1,url)                                                                          

# Output: List of all URL's & there associated Title, which are available on the input_url page
