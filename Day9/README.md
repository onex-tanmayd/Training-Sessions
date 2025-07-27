This note covers essential networking commands and tools that are fundamental for troubleshooting connectivity, analyzing network issues, and securely interacting with remote servers.

---

## 1. **ping** – Check Basic Reachability
- **Purpose:**  
  - To test if a host or server is reachable over the network.
  - Measures the round-trip time for packets sent from the local machine to a remote host.

- **How It Works:**  
  - Sends **ICMP Echo Request** packets to the target and waits for **ICMP Echo Reply** packets.
  - If replies are received, the host is reachable.

- **Syntax:**
  ```bash
  ping <hostname or IP>
  ```
- **Example:**
  ```bash
  ping google.com
  ```
- **Common Options:**
  - `-c <count>` : Limit number of packets (e.g., `ping -c 4 google.com`).
  - `-i <interval>` : Set the interval between packets.
  - `-t` (Windows): Run ping until stopped manually.

- **Use Cases:**
  - Check if a remote server is up.
  - Troubleshoot network connectivity or DNS resolution.
  - Measure latency (round-trip time).

---

## 2. **curl** – HTTP/API Testing
- **Purpose:**  
  - Transfer data to/from servers using protocols like **HTTP, HTTPS, FTP, and more**.
  - Useful for testing REST APIs, web servers, and endpoints.

- **Basic Syntax:**
  ```bash
  curl <URL>
  ```
  Example:
  ```bash
  curl http://localhost:8080
  ```

- **Key Options:**
  - `-v` : Verbose mode (shows headers and connection details).
    ```bash
    curl -v http://example.com
    ```
  - `-O` : Download a file (saves it with the same name).
    ```bash
    curl -O http://example.com/file.zip
    ```
  - `-L` : Follow redirects.
  - `-X POST` : Send POST requests (used for APIs).
    ```bash
    curl -X POST https://jsonplaceholder.typicode.com/posts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "foo",
    "body": "bar",
    "userId": 1
  }'
    ``````
  - `-H` : Add custom headers (e.g., `-H "Content-Type: application/json"`).
  - `-d` : Send data (e.g., `-d '{"key":"value"}'`).

- **Use Cases:**
  - Testing if a web server is responding.
  - Debugging API endpoints.
  - Downloading files or web content.

---

## 3. **wget** – Alternative Downloader
- **Purpose:**  
  - A command-line utility for non-interactive file downloads.
  - Often used in scripts and automation.

- **Syntax:**
  ```bash
  wget <URL>
  ```
  Example:
  ```bash
  wget http://example.com/file.zip
  ```

- **Key Features:**
  - Can download entire websites recursively:
    ```bash
    wget -r http://example.com
    ```
  - Supports resuming downloads with `-c`.
  - Runs in the background (good for large files).

- **Use Cases:**
  - Automating file downloads.
  - Mirroring websites.
  - Alternative to `curl` for file transfers.

---

## 4. **netstat / ss** – Check Listening Ports and Connections
- **Purpose:**  
  - Displays active network connections, routing tables, and listening ports.

- **Examples:**
  ```bash
  netstat -tulpn
  ```
  ```bash
  ss -tulpn
  ```

- **Flags Explained:**
  - `-t` : Show TCP connections.
  - `-u` : Show UDP connections.
  - `-l` : Show listening sockets.
  - `-p` : Show the process ID/program name.
  - `-n` : Show numeric addresses (skip DNS resolution).

- **Why use `ss` instead of `netstat`?**
  - `ss` is faster and more modern.
  - Available by default on most Linux distributions.

- **Use Cases:**
  - Check which ports are open.
  - Identify services running on specific ports (e.g., `8080`, `22`).
  - Troubleshoot port conflicts.

---

## 5. **ifconfig / ip addr** – View Network Interfaces and IPs
- **Purpose:**  
  - Display and configure network interfaces.

- **Examples:**
  ```bash
  ifconfig
  ```
  ```bash
  ip addr show
  ```

- **Notes:**
  - `ifconfig` is deprecated on some Linux systems.
  - `ip addr` (from `iproute2`) is the recommended modern alternative.

- **Use Cases:**
  - Check local IP address.
  - Verify network interface configuration.
  - Troubleshoot interface issues.

---

## 6. **ssh** – Secure Remote Login
- **Purpose:**  
  - Connect securely to remote servers via the **SSH protocol**.

- **Syntax:**
  ```bash
  ssh user@remote_server
  ```

- **Key-based Authentication (Intro):**
  1. **Generate SSH Key Pair:**
     ```bash
     ssh-keygen
     ```
     - Creates `~/.ssh/id_rsa` (private key) and `~/.ssh/id_rsa.pub` (public key).
  2. **Copy Public Key to Server:**
     ```bash
     ssh-copy-id user@remote_server
     ```

- **Use Cases:**
  - Manage remote servers (Linux/Unix).
  - Secure communication without passwords (key-based login).
  - Port forwarding (advanced use).

---

## 7. **scp / sftp** – Secure File Transfers
### **scp (Secure Copy)**:
- **Purpose:** Copy files between local and remote servers.
- **Syntax:**
  ```bash
  scp localfile.txt user@remote_server:/path/to/destination/
  ```
- **Examples:**
  - Copy from local to remote:
    ```bash
    scp test.txt user@192.168.1.100:/home/user/
    ```
  - Copy from remote to local:
    ```bash
    scp user@192.168.1.100:/home/user/test.txt .
    ```

### **sftp (Secure FTP)**:
- **Purpose:** Interactive file transfer over SSH.
- **Syntax:**
  ```bash
  sftp user@remote_server
  ```
- **Commands inside SFTP:**
  - `ls` : List files.
  - `get file.txt` : Download file.
  - `put file.txt` : Upload file.

---

## Quick Tips
- Use `man <command>` (e.g., `man curl`) for detailed documentation.
- Use `ss` over `netstat` for faster results.
- Combine **ping + curl** for diagnostics:
  - `ping` checks if the server is reachable.
  - `curl` checks if the web application is responding.
- Use `ssh -i keyfile` for key-based connections when managing servers.

---
