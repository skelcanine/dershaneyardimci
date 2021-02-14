<?php 

    require('connection.php');
    
	define('SERVER_API_KEY', 'x');
	
	

function sendNotiTeacher($topic)
 {	        
     
     
        $sql ="SELECT * FROM users Where topic='$topic'";
	    $Query = mysqli_query($conn,$sql)or die(mysqli_error($conn));
		
	    while($row = mysqli_fetch_array($Query))
	    {
 
                $registrationIds[]=$row['fbasekey'];
                
                }

	
	
	$header = [
		'Authorization: Key=' . SERVER_API_KEY,
		'Content-Type: Application/json'
	];

	$msg = [
		'title' => 'Yeni soru sisteme geldi',
		'content' => $topic.' dersinden bir soru yuklendi',
		
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