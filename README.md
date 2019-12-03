## in-or-out

### How to Run: 
1. Open up https://inorout.herokuapp.com
2. Enjoy!

### Deployment (Locally):
1. Install Eclipse (IDE), Tomcat 9 Server and Java JDK
2. Import inORout files from github to Eclipse
3. Add gson JARs to External JARs in Run Configurations
4. Add JRE library 
5. Contact us to get the SQL json file, add it as an Environment variable to connect with Google Cloud SQL
6. Get your own API keys and put them into a config file inside WEB-INF/libs/ with the format: 
> Yelp=[Yelp API Key]

> EdamamKey=[Application Key]

> EdamamId=[Application Id]

7. Run Homepage.jsp with Tomcat to open the Home Page of our website on your browser to start browsing. Have fun!
