#!/bin/bash
num_requests=2

for i in $(seq 1 $num_requests); do
  curl -X POST "http://localhost:8080/api/market/buy?itemNumber=1&buyQuantity=15" &
done

wait
echo "All requests completed."
