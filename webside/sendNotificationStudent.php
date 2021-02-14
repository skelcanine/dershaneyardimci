<?php 

    require('connection.php');
    
	define('SERVER_API_KEY', 'x');
	
	    

function sendNotiStudent($topic,$qid)
 {	      
     
     
        $sql ="SELECT * FROM $topic Where id='$qid'";
	    $Query = mysqli_query($conn,$sql)or die(mysqli_error($conn));
	    
	    while($row = mysqli_fetch_array($Query))
	    {
                $stid=$row['name'];
               }

	
	    $sql2 ="SELECT * FROM users Where id='$stid'";
	    $Query2 = mysqli_query($conn,$sql2)or die(mysqli_error($conn));
	
	    while($row = mysqli_fetch_array($Query2))
	    {
                $fbasekey=$row['fbasekey'];
                
               }
	
	
	$registrationIds[]=$fbasekey;
	
	
	
	$header = [
		'Authorization: Key=' . SERVER_API_KEY,
		'Content-Type: Application/json'
	];

	$msg = [
		'title' => 'Sorunuza cevap geldi',
		'content' => $topic.' dersinden '.$qid.' numarali sorunuz cevaplandi',
		
	];

	$payload = [
		'registration_ids' 	=> $registrationIds,
		'data'				=> $msg
	];

	$curl = curl_init();

	curl_setopt_array($curl, array(
	  CURLOPT_URL => "https://fcm.googleapis.com/fcm/send",
	  CURLOPT_RETURNTRANSFER => true,
	  CURLOPT_CUSTOMREQUEST => "POST",
	  CURLOPT_POSTFIELDS => json_encode( $payload ),
	  CURLOPT_HTTPHEADER => $header
	));

	$response = curl_exec($curl);
	$err = curl_error($curl);

	curl_close($curl);

	if ($err) {
	  echo "cURL Error #:" . $err;
	} else {
	  
	}
 }
 
 
 ?>