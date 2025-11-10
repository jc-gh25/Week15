/* -------------------------------------------------------------
   Sample data for the Pet Store assignment
   ------------------------------------------------------------- */

/* Pet stores */
INSERT INTO pet_store (pet_store_id, pet_store_name, pet_store_address,
                       pet_store_city, pet_store_state, pet_store_zip,
                       pet_store_phone)
VALUES
  (1, 'Happy Paws',   '123 Main St', 'Springfield', 'IL', '62701', '(555) 111-2222'),
  (2, 'Furry Friends','456 Oak Ave', 'Shelbyville', 'IL', '62565', '(555) 333-4444');

/* Employees */
INSERT INTO employee (employee_id, pet_store_id,
                      employee_first_name, employee_last_name,
                      employee_phone, employee_job_title)
VALUES
  (1, 1, 'Alice',   'Anderson', '(555) 101-0101', 'Store Manager'),
  (2, 1, 'Bob',     'Brown',    '(555) 102-0202', 'Cashier'),
  (3, 2, 'Carol',   'Clark',    '(555) 103-0303', 'Veterinarian'),
  (4, 2, 'David',   'Doe',      '(555) 104-0404', 'Sales Associate');

/* Customers */
INSERT INTO customer (customer_id,
                      customer_first_name, customer_last_name,
                      customer_email)
VALUES
  (1, 'Emily', 'Evans', 'emily.evans@example.com'),
  (2, 'Frank', 'Foster','frank.foster@example.com'),
  (3, 'Grace', 'Green', 'grace.green@example.com');

/* Junction table – which customers belong to which stores */
INSERT INTO pet_store_customer (pet_store_id, customer_id)
VALUES
  (1, 1),   -- Emily shops at Happy Paws
  (1, 2),   -- Frank also shops at Happy Paws
  (2, 2),   -- Frank also shops at Furry Friends
  (2, 3);   -- Grace shops at Furry Friends