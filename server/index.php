<?php 
include "Server.php";

function SynchData($device) {
	$server = new Server();
	 
	if (!isset($_GET['action'])){

	  return($server->SynchData($device));
	}
	else if ($_GET['action'] == "1"){
	  return($server->SynchData($device));
	}
	else if ($_GET['action'] == "2"){
	  return($server->SetIsSynched($synchIds));
	}
	else{
	  return "<DataSynchResult><error>wrong action variable set. (action variable = ".$_GET['action'].")</error></DataSynchResult>";
	}
} 

print SynchData($device);


?>