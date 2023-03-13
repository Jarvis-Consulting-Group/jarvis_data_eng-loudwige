#!/bin/bash

#Create function to get hardware specification
function get_hardware_specification() {
  local lscpu_out=$1
  cpu_number=$(echo "$lscpu_out" | grep -E "^CPU\(s\):" | awk '{print $2}' | xargs)
  cpu_architecture=$(echo "$lscpu_out" | awk '/Architecture/ {print $2}')
  cpu_model=$(echo "$lscpu_out" | grep "Model name:" | awk -F: '{print $2}' | sed 's/^[[:space:]]*//')
  cpu_mhz=$(echo "$lscpu_out" | grep "CPU MHz" | awk '{print $3}' | xargs)
  l2_cache=$(echo "$lscpu_out" | grep "L2 cache:" | awk '{print $3}' | sed 's/[^0-9]*//g')
  timestamp=$(vmstat -t | awk -v col="18" '{print $col, $19}'|tail -1|xargs)
  total_mem=$(vmstat --unit M | tail -1 | awk '{print $4}')
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

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5


if [ "$#" -ne 5 ]; then
  echo "Illegal number of parameters"
  exit 1
fi

# Get hostname
hostname=$(hostname -f)

#Get hardware specification
lscpu_out=`lscpu`

get_hardware_specification "$lscpu_out"

#Insert statement to insert hardware specification into host_info table
insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache,
                                   timestamp, total_mem) VALUES ('$hostname','$cpu_number','$cpu_architecture',
                                                                  '$cpu_model','$cpu_mhz','$l2_cache','$timestamp',
                                                                  '$total_mem')"

insert_data "$insert_stmt" "$psql_host" "$psql_port" "$db_name" "$psql_user" "$psql_password"

