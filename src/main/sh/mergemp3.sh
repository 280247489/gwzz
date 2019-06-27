#!/bin/bash
train_file=filelist.txt
out_file=output.mp3
cur_dir=$(cd `dirname $0`; pwd)
echo $cur_dir
#删除output.mp3
if [  -f "$out_file" ]; then 
	echo $out_file
 rm -rf $out_file 
fi 

for i in `find $cur_dir -name "*.mp3" | sort -n`
do
    echo "file '$i'"
done  >$train_file
/usr/local/bin/ffmpeg -f concat -safe 0 -i $train_file -c copy $out_file
ffmpeg_pid=$!
while kill -0 "$ffmpeg_pid"; do sleep 1; done > /dev/null 2>&1
rm -rf $train_file
exit