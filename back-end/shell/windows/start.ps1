$rootPath = Resolve-Path ../../
$jarPath = [System.IO.Path]::Combine($rootPath, "boot", "GuGu_Simple_Upload.jar")
$springConfigPath = [System.IO.Path]::Combine($rootPath, "config", "application.yml")
$logConfigPath = [System.IO.Path]::Combine($rootPath, "config", "logback-spring.xml")
$keyPath = [System.IO.Path]::Combine($rootPath, "config", "password.key")
if ( [System.IO.File]::Exists($keyPath))
{
    $key = Get-Content $keyPath
}
else
{
    $key = Read-Host Enter project key
}
java -XX:+HeapDumpOnOutOfMemoryError -jar $jarPath --jasypt.encryptor.password = $key --spring.config.location = $springConfigPath --logging.config = $logConfigPath