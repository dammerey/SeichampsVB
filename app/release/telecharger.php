<?php
$nomFichier = "SeichampsVB_2.0_20250818.apk"; // ton APK
header("Content-Type: application/vnd.android.package-archive");
header("Content-Disposition: attachment; filename=\"$nomFichier\"");
header("Content-Length: " . filesize($nomFichier));
readfile($nomFichier);
exit;
?>