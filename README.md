# juan_Code
Juan Pablo Ceron personal projects


# CarRegistry

CarRegistry es una aplicación de registro diario de coches que permite realizar operaciones CRUD en una base de datos relacional MySQL. Además, cuenta con un sistema de registro de usuarios que utiliza tokens para acceder y realizar distintas operaciones según el rol asignado.

## Descripción

CarRegistry es una herramienta diseñada para gestionar eficientemente el registro diario de coches en una base de datos. Con esta aplicación, los usuarios pueden realizar las siguientes operaciones:

- **Crear**: Agregar nuevos registros de coches con información detallada, como modelo, marca, año, etc.
- **Leer**: Consultar y visualizar registros existentes en la base de datos.
- **Actualizar**: Modificar información de registros de coches existentes, como actualizar el estado o la información relacionada.
- **Eliminar**: Eliminar registros de coches que ya no sean necesarios.

Además, CarRegistry implementa un sistema de autenticación de usuarios que asigna roles y privilegios basados en tokens. Esto permite un acceso seguro y restringido a las funcionalidades de la aplicación:

- **Usuarios Administradores (Vendor)**: Tienen acceso completo a todas las funcionalidades de la aplicación, incluyendo operaciones CRUD y gestión de usuarios.
- **Usuarios Estándar (Client)**: Tienen acceso limitado y solo pueden realizar operaciones de lectura y algunas operaciones específicas, según la configuración de permisos.


# CarRegistry

CarRegistry is a daily car registration application that allows CRUD operations on a MySQL relational database. Additionally, it features a user registration system that uses tokens to access and perform different operations based on the assigned role.

## Description

CarRegistry is a tool designed to efficiently manage the daily registration of cars in a database. With this application, users can perform the following operations:

- **Create**: Add new car records with detailed information such as model, brand, year, etc.
- **Read**: Query and visualize existing records in the database.
- **Update**: Modify information of existing car records, such as updating the status or related information.
- **Delete**: Remove car records that are no longer needed.

In addition, CarRegistry implements a user authentication system that assigns roles and privileges based on tokens. This allows secure and restricted access to the application's functionalities:

- **Administrators (Vendor)**: Have full access to all application functionalities, including CRUD operations and user management.
- **Standard Users (Client)**: Have limited access and can only perform read operations and some specific operations, depending on the permission settings.
