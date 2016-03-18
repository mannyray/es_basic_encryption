#!/bin/bash
#Parameter:
#    $1: <server_address>
#    $2: <n_port> ~ port used by server 
#    $3: <password file>
#    $4: <query file>

#For Java implementation
java client $1 $2 $3 "$4"
