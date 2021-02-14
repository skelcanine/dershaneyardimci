<?php

require('connection.php');
    
if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 $DefaultId = 1;
 
 $ImageData = $_POST['image_path'];
 
 $id = $_POST['id'];
 $topic = $_POST['topic'];
 
 $GetOldIdSQL ="SELECT id FROM $topic ORDER BY id ASC";
 
 $Query = mysqli_query($conn,$GetOldIdSQL)or die(mysqli_error($conn));
 
 while($row = mysqli_fetch_array($Query)){
 
 $DefaultId =((int) $row['id'])+1;
 }
 
 $ImagePath = "$topic/$DefaultId.jpg";
 
 $ServerURL = "https://egematx.000webhostapp.com/$ImagePath";
 
 $InsertSQL = "insert into $topic (path,name) values ('$ServerURL','$id')";
 
 if(mysqli_query($conn, $InsertSQL)){

 file_put_contents($ImagePath,base64_decode($ImageData));

 echo "Resminiz basariyla yuklendi.";
 }
 
 mysqli_close($conn);
 }else{
 echo "Resminiz yuklenemedi lutfen gelistirici ile iletisime geciniz.";
 }
 
 ?>