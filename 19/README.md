# 19
Приложение



<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Appointment Form</title>
</head>
<body>
    <h2>Appointment Form</h2>
    <form action="#" method="post">
        <label for="time">Time:</label>
        <select id="time" name="time">
            <option value="09:00">09:00</option>
            <option value="10:00">10:00</option>
            <option value="11:00">11:00</option>
            <option value="12:00">12:00</option>
            <option value="13:00">13:00</option>
            <option value="14:00">14:00</option>
            <option value="15:00">15:00</option>
        </select>
        <br/><br/>

        <label for="master">Choose a Master:</label>
        <select id="master" name="master">
            <option value="1">John Doe</option>
            <option value="2">Jane Smith</option>
            <option value="3">Emily Johnson</option>
            <option value="4">Michael Brown</option>
        </select>
        <br/><br/>

        <button type="submit">Submit</button>
    </form>
</body>
</html>
