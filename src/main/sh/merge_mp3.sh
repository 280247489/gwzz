#!/bin/bash
cur_dir=$(cd `dirname $1`; pwd)
date_name=$(date +%Y%m%d%H%M%S)
out_file="merge_"$date_name".mp3"
#删除
for j in `find $1 -name "merge_*.mp3"`
do
    rm -rf $j
done
/usr/local/bin/ffmpeg -f concat -safe 0 -i $2 -c copy $1$out_file > /dev/null 2>&1
ffmpeg_pid=$!
while kill -0 "$ffmpeg_pid"; do sleep 1; done > /dev/null 2>&1
if [  -f "${1}$out_file" ]; then
      chown memory:memory $1$out_file
    #echo $1$out_file
    echo ${1#*:}$out_file

else
   echo -e "Fail"&&exit
fi
exit
