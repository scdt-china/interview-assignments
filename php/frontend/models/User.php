<?php
namespace frontend\models;
use Yii;
use yii\db\ActiveRecord;

class User extends ActiveRecord {
    public static function tableName() {
        return '{{tbl_user}}';
    }
}