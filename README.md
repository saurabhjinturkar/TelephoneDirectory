# TelephoneDirectory

<i><b>Installation instruction</b> (two ways):</i>

<i>1) Copy the apk of the app in one of your mobile phone folder. Than launch the APK directly from the phone to install it.</i>

<i>2) Open the project with Android Studio, connect your device to the computer and run it.</i>




<h6>You have to build a mobile application that provides a simple telephone directory.</h6>

<b>Technicalities</b>

You may use whatever programming language/platform you prefer. Use something that you know well.
You must release your work with an OSI-approved open source license of your choice.
You must deliver the sources of your application, with a README that explains how to compile and run it.

<b>Entries</b>
<div>
The application handles a set of entries, that contain a first name, last name, and a telephone number.
The entries should be validated, so that it's not possible to enter an empty first or last name; and the phone number should be of the form

+39 02 1234567

That is a "+" followed by a nonempty group of digits, a space, a nonempty group of digits, a space, a group of digits with at least 6 digits.

The application consists of the following pages:
<ol>
<li> <b>Home page</b> </li>

  Contains a text field that allows to search through all the entries by name or number. When I enter text in the      field, the page will be reloaded with a table containing all the entries that match the text I entered.
  The page contains a link to the "add new entry" page.
  When an entry is displayed, it contains a link to the "edit this entry" page.

<li> <b>Add new entry page</b> </li>

  Contains a form for entering a new entry.

<li> <b>Edit entry page</b> </li>

  Contains a form for modifying an existing entry.

<li> <i>Extra activities (not mandatory)</i> </li>

  Commit your code on github or any other SCM repository you prefer (e.g. bitbucket, gitlab, etc)
