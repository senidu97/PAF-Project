
<html>
<head>
    <meta charset="UTF-8">
    <title>Check Out</title>
    <style type="text/css">
        table { border: 0; }
        table td { padding: 10px; }
    </style>
</head>
<body>
<div align="center">
    <h1>Welcome to HealthCare Payments!</h1>
    <br/>
    <form action="authorize_payment" method="post">
    <table>
        <tr>
            <td>Appointment ID:</td>
            <td><input type="text" name="appointmentID" value = "APP01" /></td>
        </tr>
        <tr>
            <td>Patient name:</td>
            <td><input type="text" name="username" value = "Mr.Perera" /></td>
        </tr>
        <tr>
            <td>Amount:</td>
            <td><input type="text" name="subtotal" value = "2000.00"/></td>
        </tr>    
        <tr>
            <td>Tax:</td>
            <td><input type="text" name="tax" value = "20.00"/></td>
        </tr>    
        <tr>
            <td>Total Amount:</td>
            <td><input type="text" name="amount" value = "2020.00" /></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Checkout" />
            </td>
        </tr>
    </table>
    </form>
</div>
</body>
</html>