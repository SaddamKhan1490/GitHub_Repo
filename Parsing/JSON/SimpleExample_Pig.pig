
atp_prod_loc_thld_e = load '/user/test/input_files_dir/*' using JsonLoader('product_id:chararray,threshold_setting:chararray,location_id:int,threshold_pickup_walkin_reserve:int,threshold_ship_walkin_reserve:int,published_timestamp:chararray');
rmf /user/test/output_files_dir/;
STORE atp_prod_loc_thld_e INTO '/user/test/output_files_dir/' using PigStorage ('|');


atp_prod_thld_output_e= load '/user/test/input_files_dir/*' using JsonLoader('department_id:int,class_id:int,item_id:int,threshold_setting:chararray,threshold_quantity:int,threshold:(H:int,L:int,M:int),published_timestamp:chararray');
atp_prod_thld_e = foreach atp_prod_thld_output_e generate department_id,class_id,item_id,threshold_setting, threshold_quantity, flatten(threshold), published_timestamp;
rmf /user/test/output_files_dir/;
STORE atp_prod_thld_e INTO '/user/test/output_files_dir/' using PigStorage (',');


atp_loc_thld_output_e = load '/user/test/input_files_dir/*' using JsonLoader('threshold_group:chararray, threshold_factor:int, threshold_minimum:int, threshold_maximum:int, location_ids:{t:(i:int)}, published_timestamp:chararray');
atp_loc_thld_e = foreach atp_loc_thld_output_e generate threshold_group,threshold_factor,threshold_minimum,threshold_maximum, flatten(location_ids), published_timestamp;
rmf /user/test/output_files_dir/;
STORE atp_loc_thld_e INTO '/user/test/output_files_dir/' using PigStorage ('|');
