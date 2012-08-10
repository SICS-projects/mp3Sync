<?php 
function SynchData($device) {
	$server = &new Server();
	 
	return($server->SynchData($device));
} 

$server = new SoapServer("synchData.wsdl"); 
$server->addFunction("SynchData");
$server->handle(); 
?>