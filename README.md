Shape service

1. Run the application with this command: ./gradlew bootRun.

2. The application contains a web app and a REST Service, running on port 8081. Due to limited time, I was not able to extract them into 3 smaller services (Athorization, Shape service and web app).

3. The REST API exposes some of the endpoints, protected by Basic Authentication (Not ideal for mobile app, oAuth2 would be better)
Username: admin
Password: admin

3.1. Get all categories:
GET: locahost:8081/api/categories

3.2. Get all shapes:
GET: localhost:8081/api/shapes

3.3. Create a new shape:
POST: localhost:8081/api/shapes
Payload:
{
	"shapeName": "My new rectangle",
	"sizes": {
		"width": 10,
		"length": 10
	},
	"shapeCategory": {
		"shapeCategoryName": "Rectangle"
	}
	
}

3.4. Calculate area of a shape:
POST: localhost:8081/api/shapes/area
Payload:
{
	"shapeName": "My circle",
	"sizes": {
		"size": 123
	}
	
}

3.5. Check if a shape falls under other categories
POST: localhost:8081/api/shapes/other_categories
Payload:
{
	"shapeName": "My new rectangle",
	"sizes": {
		"width": 10,
		"length": 10
	},
	"shapeCategory": {
		"shapeCategoryName": "Rectangle"
	}
}

4. Login the web app using the above username password or signup at localhost:8081/signup.
Regular user should be able to see shapes that created for him.
Admin should be able to manage shapes, categories and other users.

5. While creating new categories please note that dimensions should be separated by commas:
Ex: width, length, size
Not: width length.size

Rules and area formulas terms should be separated by spaces:
Ex: width + length
Not: width+length 

Ex: width > 0 && length > 0
Not width>0&&length>0

Usually these input values should be validated and users should be enforced to follow by the frontend but due to limited time, I wasn't able to implement it.

6. In User Management screen, for role input, make sure you enter either ROLE_USER or ROLE_ADMIN(other values are fine, the user will still be created, just might encounter some small errors)

7. When creating a new rule that allows shape to fall under another category, remember to click "Add" button to add the rule.






















