#!/bin/bash
train_file=filelist.txt
#out_file=output.mp3
cur_dir=$(cd `dirname $1`; pwd)
date_name=$(date +%Y%m%d%H%M%S)
out_file="merge_"$date_name".mp3"
#echo $cur_dir
#删除
for j in `find $1 -name "merge_*.mp3"`
do
    #echo "$j"
   # echo $cur_dir
    rm -rf $j
done

for i in `find $1 -name "*.mp3" |  sort -n  -k 6 -t /`
do
    echo "file '$i'"
done  > $1$train_file
chown memory:memory ${1}${train_file}
#cat $1$train_file
/usr/local/bin/ffmpeg -f concat -safe 0 -i $1$train_file -c copy ${1}${out_file} > /dev/null 2>&1
ffmpeg_pid=$!
while kill -0 "$ffmpeg_pid"; do sleep 1; done > /dev/null 2>&1
rm -rf $1$train_file
if [  -f "${1}$out_file" ]; then
      chown memory:memory ${1}${out_file}
    #echo $1$out_file
    echo ${1#*:}${out_file}

else
   echo -e "Fail"&&exit
fi
exit
