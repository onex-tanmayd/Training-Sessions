# 🔧 Linux Monitoring & Process Management – Cheat Sheet

A quick reference for commonly used commands to monitor system performance, disk usage, networking, and process control in Linux.

---

## 🧮 System Resource Monitoring

### 📂 Disk Usage

```bash
df -h
```

* Shows mounted disk partitions and available space
* `-h` = human-readable format (e.g. GB, MB)

```bash
du -sh *
```

* Shows individual directory sizes
* `-s` = summary, `-h` = human-readable

---

### 📈 CPU & Memory

```bash
top
```

* Real-time process and resource monitor

```bash
free -h
```

* Displays memory usage
* `-h` = human-readable
* Alternatives: `-m` for MB, `-g` for GB

---

## 🔍 Process Management

### 🔎 View Running Processes

```bash
ps -ef | grep doris
```

* Lists all processes and filters for "doris"
* `-e` = all processes
* `-f` = full details (UID, PID, PPID, etc.)

---

### ❌ Killing Processes

```bash
kill <PID>
```

* Gracefully terminates the process, allowing it to save state

```bash
kill -9 <PID>
```

* Force kills the process (cannot be trapped or ignored)

---

## 🛜 Networking

```bash
netstat -tulpn
```

* Lists open ports and associated services
* `-t` = TCP, `-u` = UDP
* `-l` = listening
* `-p` = show PID/program name
* `-n` = numeric IP/port instead of resolving names

---

## ⚙️ Running Background Jobs

### 🧪 Background with Output Redirect

```bash
python hello.py > hello_log.txt 2> hello_err.txt
```

* Standard output (`stdout`) → `hello_log.txt`
* Error output (`stderr`) → `hello_err.txt`

---

### 🏃 Run in Background with No Output

```bash
nohup python3 hello.py > /dev/null 2>&1 &
```

* `nohup` = ignore hangups (keeps running after logout)
* `> /dev/null` = discard standard output
* `2>&1` = redirect errors to standard output (which is discarded)
* `&` = run in background

**⚠️ Note:**
Processes run in background with `&` can lock the terminal if they don’t detach properly. Kill them using their PID if needed.

