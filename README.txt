Emailer is a program that allows you to send emails to members of a group who have signed up for a particular time slot in a 24 hour day. Currently, it's designed to send out an email to those who sign up for 30 minute time slots to pray, although I have plans to make the email your users receive configurable.

The current format of a CSV file supported and parsed by emailer is set up like so:

-----------------------------------------------------------------------------------------
Date (e.g. Monday November 15th) | 12:00 AM       | 12:30AM    | ... | 11:00 PM | 11:30PM
Name                             | John Smith     | Jane Doe   | ... |          | 
Phone Number                     | 1234567890     | 9876543210 | ... |          | 
Email                            | js@example.com | jd@foo.com | ... |          | 

Emailer was built in Eclipse, so it should be fairly simple to get an environment set up and running in there. Whether or not you use Eclipse, make sure you include the required libraries. 

Use the config file in the "res" directory to add your own email server, email, username, and password necessary for authentication to the email server.