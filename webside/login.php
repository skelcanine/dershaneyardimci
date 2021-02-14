<?php

require('connection.php');


if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
	$username = $_POST['username'];
	$password = $_POST['password'];
	$fbasekey = $_POST['fbasekey'];

$sql="SELECt * FROM users WHERE username = '$username'";

$result = mysqli_query($conn,$sql);
$row = mysqli_fetch_array($result,MYSQLI_ASSOC);

if($row['password']==$password)
{
    
    $myObj['username']=$row['username'];
    $myObj['password']=$row['password'];
    $myObj['topic']=$row['topic'];
    $myObj['id']=$row['id'];
    $myObj['fbasekey']=$row['fbasekey'];
    $myObj['success']="1";
    
    
 if(strlen($fbasekey)>4)
 {
     $sql2="UPDATE users SET fbasekey='$fbasekey' WHERE username='$username'";
     $rresult=mysqli_query($conn,$sql2);
     
 }

echo json_encode($myObj);
}
}else echo "ftrue";

?>