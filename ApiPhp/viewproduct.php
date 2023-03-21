<?php
include "connect.php";
$iduser = $_POST['iduser'];


$query = 'SELECT * FROM `order` WHERE `iduser` = '.$iduser;

$data = mysqli_query($conn,$query);
$result = array();
while($row = mysqli_fetch_assoc($data)) {
	$query1 = 'SELECT * FROM `orderdetail` INNER JOIN newproduct ON orderdetail.id = newproduct.id WHERE orderdetail.id = '.$row['id'];
	$data1 = mysqli_query($conn,$query1);
	$item = array();
	while($row1 = mysqli_fetch_assoc($data)) {
		$item[] = $row1;
	}
	$row['item'] = $item;
	$result[] = ($row);


}
if(!empty($result)) {
	$arr = [
		'success' => true,
		'message' => "thanh cong",
		'result' => $result

	];

}else{
	$arr = [
		'success' => false,
		'message' => " khong thanh cong",
		'result' => $result

	];
}
print_r(json_encode($arr));
?>