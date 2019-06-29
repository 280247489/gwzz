#!/bin/bash
train_file=filelist.txt
out_file=output.mp3
cur_dir=$(cd `dirname $1`; pwd)
#echo $cur_dir
#删除output.mp3
if [  -f "${1}${out_file}" ]; then
   #     echo $out_file
 rm -rf ${1}${out_file}
fi

for i in `find $cur_dir -name "*.mp3" | sort -n`
do
    echo "file '$i'"
done>${1}$train_file
/usr/local/bin/ffmpeg -f concat -safe 0 -i ${1}$train_file -c copy ${1}${out_file} > /dev/null 2>&1
ffmpeg_pid=$!
while kill -0 "$ffmpeg_pid"; do sleep 1; done > /dev/null 2>&1
#rm -rf $train_file
if [  -f "${1}$out_file" ]; then
    #    echo  -e "${1}${out_file}"
      chown memory:memory ${1}${out_file}
        echo ${1}${out_file}
        # echo  -e "${1}${out_file}"

else
   echo -e "Fail"&&exit
fi
exit
