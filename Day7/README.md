### DAY 7 Progress:

* Learned about file permissions and how they’re structured into three groups: **user**, **group**, and **others**.
* Each group has 3 types of permissions:

  * **r**: read
  * **w**: write
  * **x**: execute
  * **-**: no permission

Permissions can be modified using the `chmod` command.

### Examples:

* **Adding a permission**:
  `$ chmod u+x myfile`
  This gives the **user** execute permission on `myfile`.

* **Removing a permission**:
  `$ chmod u-x myfile`
  This removes the execute permission from the **user**.

* **Adding multiple permissions**:
  `$ chmod ug+w myfile`
  This gives both **user** and **group** write permission.

### Numeric format:

Permissions can also be changed using numbers instead of letters:

* **4**: read
* **2**: write
* **1**: execute

Example:
`$ chmod 755 myfile`

* **7 (user)**: read, write, execute
* **5 (group)**: read, execute
* **5 (others)**: read, execute

The numbers are a sum of permissions:

* 7 = 4 (read) + 2 (write) + 1 (execute)
* 5 = 4 (read) + 1 (execute)

