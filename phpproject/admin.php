<?php include_once '_common.php';?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php
    require_once $g['path_layout'] . 'default/includes/_import.head.php';
    ?>
</head>
<body>
<?php
require_once $g['path_layout'] . 'default/includes/header.php';
?>

<!-- 메인 화면 -->
<main class="container-fluid">
    <div class="container main-container">
        <!-- body 화면 -->
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body" id="panel_content">
                        <?php require_once $g['path_module'] . 'admin/adminMenu.php';?>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<div id="ajaxPath" data-path="<?php echo $g['path_page'] . 'process/'; ?>" ></div>
<div id="dialog"></div>
<div id="actionPath" data-path="<?php echo $g['path_module'] . 'admin/'; ?>" ></div>
</body>

</html>


