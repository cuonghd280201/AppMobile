<?php
include "connect.php";
$phone = $_POST['phone'];
$email = $_POST['email'];
$total = $_POST['total'];
$iduser = $_POST['iduser'];
$quality = $_POST['quality'];
$address = $_POST['address'];
$orderdetail = $_POST['orderdetail'];

$query = 'INSERT INTO `order`(`iduser`, `email`, `address`, `quality`, `phone`, `total`) VALUES ('.$iduser.',"'.$email.'","'.$address.'","'.$quality.'","'.$phone.'","'.$total.'")';

$data = mysqli_query($conn, $query);
if ($data == true){
	$query = 'SELECT id AS idonghang FROM `order` WHERE `iduser` = '.$iduser.' ORDER BY id DESC LIMIT 1';
	$data = mysqli_query($conn, $query);

	while($row = mysqli_fetch_assoc($data)) {
	$idonghang = ($row);

}
if(!empty($idonghang)){
	$orderdetail = json_decode($orderdetail, true);
	foreach ($orderdetail as $key => $value) {
		// code...
		$truyvan = 'INSERT INTO `orderdetail`(`idorder`, `id`, `quality`, `price`) VALUES ('.$idonghang["idonghang"].','.$value["id"].','.$value["quality"].',"'.$value["price"].'")';
			echo $truyvan;
			$data = mysqli_query($conn, $truyvan);
		}
			
		if($data == true){
				$arr = [
		'success' => true,
		'message' => "thanh cong"
		

	];

			}else{
				$arr = [
		'success' => false,
		'message' => " khong thanh cong"
		

	];
			}


	print_r(json_encode($arr));

}

	
}else{
	$arr = [
		'success' => false,
		'message' => " khong thanh cong"
		

	];
	print_r(json_encode($arr));
}


?>