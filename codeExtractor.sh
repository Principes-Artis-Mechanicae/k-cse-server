#!/bin/bash

# 상대 경로로 지정한 폴더를 첫 번째 인자로 받음
folder_path=$1

# 출력 파일 지정
output_file="output2.txt"

# 기존에 존재하는 output.txt 파일 삭제 (있을 경우)
if [ -f "$output_file" ]; then
  rm "$output_file"
fi

# 재귀적으로 폴더 안의 모든 .java 파일을 찾고, 각 파일의 내용을 output.txt에 추가
find "$folder_path" -type f -name "*.java" | while read java_file; do
  echo "Processing $java_file"

  # 파일 이름 출력
  echo "$(basename "$java_file")" >> "$output_file"

  # 파일 내용 중 import 및 package 구문을 제외하고 출력
  grep -Ev '^\s*(import|package)\s' "$java_file" >> "$output_file"

  # 파일 간에 구분을 위한 빈 줄 추가
  echo -e "\n" >> "$output_file"
done

echo "All .java files have been processed and saved in $output_file"
