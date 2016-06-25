#!/bin/bash
# $> bash icon-gen.sh <version label> <project dir> <script sub-dir>
# https://gist.github.com/amulyakhare/ebc784eac45ecd5df448
# https://www.reddit.com/r/androiddev/comments/3ig3gm/show_us_your_gradle_tasks/
#
# process_icon version_num res_sub_dir current_work_dir  target_dir
process_icon(){
    image_width=`identify -format %[fx:w] $3/app/src/main/res/drawable-$2/com_garena_shopee_logo_shopee_launcher.png` && let "image_width-=4"
    image_height=`identify -format %[fx:h] $3/app/src/main/res/drawable-$2/com_garena_shopee_logo_shopee_launcher.png` && let "image_height-=4"
    convert $3$4/marker.png -background '#0000' -fill white -gravity south -size 137x16 caption:$1 -composite -resize $image_widthx$image_height $3$4/intermediate.png
    convert -composite -gravity center $3/app/src/main/res/drawable-$2/com_garena_shopee_logo_shopee_launcher.png $3$4/intermediate.png $3$4/com_garena_shopee_logo_shopee_launcher.png
    cp $3$4/com_garena_shopee_logo_shopee_launcher.png $3/app/src/internal/res/mipmap-$2/
}

process_icon $1 xhdpi $2 $3
process_icon $1 xxhdpi $2 $3
process_icon $1 hdpi $2 $3
process_icon $1 mdpi $2 $3
rm $2$3/com_garena_shopee_logo_shopee_launcher.png
rm $2$3/intermediate.png