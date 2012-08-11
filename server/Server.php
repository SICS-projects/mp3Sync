<?php

class Server {
	
	public function Server(){
		
	}
	
	private function GetDataForDevice($device){
		$verbindung = mysql_connect("localhost", "username_db", "password_db");
		mysql_select_db("name_db", $verbindung);
		$dataListe = mysql_query("SELECT _id, name, filePath FROM songs WHERE belongsToUser = (SELECT belongsToUser FROM devices WHERE name = 'TestDevice')
									   AND isSynched = 0", $verbindung);

$ret_result = "<DataSynchResult>";
while($result = mysql_fetch_array($dataListe)) {
    $ret_result .= "<Data _id=\"".$result["_id"]."\" name=\"".$result["name"]."\" filePath=\"".$result["filePath"]."\" />";
  }
$ret_result .= "</DataSynchResult>";
		echo $ret_result;

	}

      

	public function SynchData($device){
		//TODO: Download New Data to Device
		return ($this->GetDataForDevice($device));
		
		//TODO: Upload New Data to Server
	}

	public function SetIsSynched($synchIds){

	      if (strpos($synchIds, ",") > -1){
		$synchIds_arr = explode(",", $synchIds);
	      }else{
		$synchIds_arr = $synchIds.",".$synchIds;
	      }

	      $verbindung = mysql_connect("localhost", "joachi_773735", "keinalkohol");
	      mysql_select_db("joachi_773735", $verbindung);

	      foreach ($synchIds_arr as &$synchId) {
		$dataListe = mysql_query("UPDATE songs set isSynched = 1 where ID = ".$synchId, $verbindung);
	      }
	}
	
	
}
