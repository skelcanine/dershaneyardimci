<?php
require('connection.php');

if($_SERVER['REQUEST_METHOD'] == 'POST')
 {

	$topic = $_POST['topic'];
	$id = $_POST['id'];


$sql="SELECT * FROM $topic WHERE name = '$id'";
$result = mysqli_query($conn,$sql);

$rows=array();
while ($row = mysqli_fetch_row($result))
{
	$rows[]=$row;
}

echo json_encode($rows);
}
?>