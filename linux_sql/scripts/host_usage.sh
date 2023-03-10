#!/bin/bash

# Function to parse vmstat output
function parse_vmstat {
  local vmstat_output=$1

  memory_free=$(echo "$vmstat_output" | tail -1 | awk -v col="4" '{print $col}'| xargs)
  cpu_idle=$(echo "$vmstat_output" | tail -1 | awk '{print $15}'| xargs)
  cpu_kernel=$(echo "$vmstat_output" | awk '{print $14}' | sed 's/[a-zA-Z]*//g')
  disk_io=$(vmstat -d | tail -1 | awk -v col="10" '{print $col}'|xargs)
  disk_available=$(df -BM --output=avail / | sed '1d;s/M//')
  timestamp=$(vmstat -t | awk -v col="18" '{print $col, $19}'|tail -1|xargs)
}

# Function to insert data into database
function insert_data {
  local insert_stmt=$1
  local psql_host=$2
  local psql_port=$3
  local db_name=$4
  local psql_user=$5
  local psql_password=$6

  export PGPASSWORD=$psql_password

  psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_stmt"

  exit $?
}

# Main script
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ "$#" -ne 5 ]; then
  echo "illegal number of parameters"
  exit 1
fi

vmstat_output=$(vmstat --unit M)
hostname=$(hostname -f)

# Parse resources specification
parse_vmstat "$vmstat_output"

# Get host ID from database
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

# Insert server usage data into host_usage table
insert_stmt="INSERT INTO host_usage(timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
VALUES('$timestamp',$host_id,'$memory_free','$cpu_idle', '$cpu_kernel','$disk_io','$disk_available')"

insert_data "$insert_stmt" "$psql_host" "$psql_port" "$db_name" "$psql_user" "$psql_password"