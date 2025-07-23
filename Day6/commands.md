# Complete Linux Shell Guide - Navigation & Essential Commands

## Table of Contents
1. [Getting Connected to Linux](#getting-connected-to-linux)
2. [Understanding the Shell Environment](#understanding-the-shell-environment)
3. [Essential Navigation Commands](#essential-navigation-commands)
4. [File Operations](#file-operations)
5. [Text Editors](#text-editors)
6. [Finding Things](#finding-things)
7. [File Transfer Commands](#file-transfer-commands)
8. [System Information & Management](#system-information--management)
9. [Shell Features & Utilities](#shell-features--utilities)
10. [Additional Useful Commands](#additional-useful-commands)
11. [Practical Exercises](#practical-exercises)

## Getting Connected to Linux

### SSH (Secure Shell) - Connecting to Remote Servers

SSH allows you to securely connect to remote Linux systems over a network.

#### Basic SSH Syntax
```bash
ssh username@hostname_or_ip
ssh username@192.168.1.100
ssh user@example.com
```

#### SSH with Specific Port
```bash
ssh -p 2222 username@hostname
```

#### SSH with Private Key
```bash
ssh -i ~/.ssh/id_rsa username@hostname
```

#### SSH Configuration File
Create `~/.ssh/config` for easier connections:
```
Host myserver
    HostName 192.168.1.100
    User myusername
    Port 22
    IdentityFile ~/.ssh/id_rsa
```

Then connect with: `ssh myserver`

#### SSH Security Tips
- Always use key-based authentication when possible
- Disable password authentication on servers
- Use non-standard ports (not 22) for public servers
- Enable fail2ban to prevent brute force attacks

### Local Terminal Access
- **Linux**: Terminal application (Ctrl+Alt+T)
- **macOS**: Terminal or iTerm2
- **Windows**: WSL (Windows Subsystem for Linux), Git Bash, or PowerShell

## Understanding the Shell Environment

### The Prompt Symbols

#### `$` (Dollar Sign)
- Indicates you're logged in as a **regular user**
- Example: `user@hostname:~/Documents$ `
- Safe to run most commands
- Limited system access

#### `#` (Hash/Pound Sign)
- Indicates you're logged in as **root** (superuser)
- Example: `root@hostname:/etc# `
- **DANGEROUS**: Can modify any system file
- Use with extreme caution!

#### Switching Users
```bash
# Switch to root (dangerous!)
sudo su -
# Or just run one command as root
sudo command

# Switch to another user
su - username
```

### Understanding Your Environment

#### `whoami` - Who Am I?
```bash
whoami
# Output: your_username
```

#### `hostname` - What System Am I On?
```bash
hostname
# Output: server-name

# Get full hostname
hostname -f
# Output: server.example.com

# Get IP address
hostname -I
```

#### Environment Information
```bash
# Current user ID and groups
id

# System information
uname -a

# Operating system details
cat /etc/os-release

# Current shell
echo $SHELL
```

## Essential Navigation Commands

### `pwd` - Print Working Directory
Shows your current location in the filesystem.

```bash
pwd
# Output: /home/username/Documents
```

### `ls` - List Directory Contents

#### Basic Usage
```bash
ls                    # List current directory
ls /path/to/dir      # List specific directory
```

#### Important Options
```bash
ls -l                # Long format (detailed)
ls -a                # Show hidden files (starting with .)
ls -h                # Human-readable file sizes
ls -la               # Combine long format + hidden files
ls -lah              # Long format + hidden + human-readable
ls -lt               # Sort by modification time (newest first)
ls -lr               # Reverse order
ls -R                # Recursive (show subdirectories)
```

#### Understanding `ls -l` Output
```bash
$ ls -l
-rw-r--r-- 1 user group 1024 Jan 15 10:30 file.txt
drwxr-xr-x 2 user group 4096 Jan 15 09:15 directory/
```

Breaking down: `-rw-r--r--`
- First character: file type (`-` = file, `d` = directory, `l` = link)
- Next 9 characters: permissions (owner, group, others)
  - `r` = read, `w` = write, `x` = execute

### `cd` - Change Directory

#### Navigation Types
```bash
# Absolute paths (start from root /)
cd /home/username/Documents
cd /etc/nginx
cd /var/log

# Relative paths
cd Documents          # Go to Documents in current directory
cd ../               # Go up one directory (parent)
cd ../../            # Go up two directories
cd ./subdirectory    # Go to subdirectory (. = current dir)

# Special shortcuts
cd ~                 # Go to home directory
cd                   # Same as cd ~ (go home)
cd -                 # Go to previous directory
cd ~username         # Go to another user's home directory
```

#### Pro Tips
```bash
# Use tab completion!
cd Doc<TAB>          # Will complete to Documents if it exists

# Go back and forth between directories
cd /var/log
cd /home/user
cd -                 # Back to /var/log
cd -                 # Back to /home/user
```

### Directory Operations

#### `mkdir` - Create Directories
```bash
mkdir new_directory
mkdir dir1 dir2 dir3                    # Create multiple directories
mkdir -p path/to/nested/directories     # Create parent directories if needed
mkdir -m 755 secure_dir                 # Set permissions while creating
```

#### `rmdir` - Remove Empty Directories
```bash
rmdir empty_directory
rmdir -p path/to/empty/nested/dirs     # Remove nested empty directories
```

## File Operations

### `touch` - Create Files or Update Timestamps
```bash
touch newfile.txt                      # Create empty file
touch file1.txt file2.txt file3.txt   # Create multiple files
touch -t 202301151030 file.txt         # Set specific timestamp
```

### `cp` - Copy Files and Directories
```bash
# Copy files
cp source.txt destination.txt
cp source.txt /path/to/destination/
cp *.txt backup/                       # Copy all .txt files

# Copy directories
cp -r source_directory/ destination/   # Recursive copy
cp -a source/ destination/             # Archive mode (preserves everything)
cp -u source.txt dest.txt              # Update only if source is newer
```

### `mv` - Move/Rename Files and Directories
```bash
# Rename
mv oldname.txt newname.txt
mv old_directory/ new_directory/

# Move
mv file.txt /path/to/destination/
mv *.log logs/                         # Move all .log files to logs directory

# Move and rename
mv source.txt /path/to/destination/newname.txt
```

### `rm` - Remove Files and Directories ⚠️ **DANGEROUS!**

#### ⚠️ **WARNING: rm is IRREVERSIBLE! Deleted files cannot be recovered easily!**

```bash
# Remove files
rm file.txt
rm file1.txt file2.txt file3.txt       # Remove multiple files
rm *.tmp                               # Remove all .tmp files

# Remove directories
rm -r directory/                       # Recursive removal
rm -rf directory/                      # Force recursive removal (VERY DANGEROUS!)

# Safe practices
rm -i file.txt                         # Interactive mode (asks for confirmation)
rm -v file.txt                         # Verbose mode (shows what's being deleted)
```

#### Safety Tips for `rm`
```bash
# Always double-check before running rm -rf
ls directory/                          # Check contents first
rm -rf directory/                      # Then delete

# Use trash instead of rm (if available)
trash file.txt                         # Moves to trash instead of permanent deletion

# Create aliases for safety
alias rm='rm -i'                       # Always ask for confirmation
```

### File Viewing Commands

#### `cat` - Display Entire File
```bash
cat file.txt                           # Display file contents
cat file1.txt file2.txt                # Display multiple files
cat -n file.txt                        # Show line numbers
cat -A file.txt                        # Show all characters (including hidden)
```

#### `less` / `more` - View Large Files Page by Page
```bash
less largefile.log                     # Better than more (can scroll backwards)
more file.txt                          # Basic pager

# Navigation in less:
# Space or f     - Next page
# b              - Previous page
# /search_term   - Search forward
# ?search_term   - Search backward
# q              - Quit
# G              - Go to end
# g              - Go to beginning
```

#### `head` - View Beginning of File
```bash
head file.txt                          # First 10 lines
head -n 20 file.txt                    # First 20 lines
head -c 100 file.txt                   # First 100 characters
```

#### `tail` - View End of File
```bash
tail file.txt                          # Last 10 lines
tail -n 20 file.txt                    # Last 20 lines
tail -f logfile.log                    # Follow file (watch for new lines) - GREAT FOR LOGS!
tail -F logfile.log                    # Follow file even if it's rotated
```

## Text Editors

### `nano` - Beginner-Friendly Editor

Nano is the most user-friendly command-line text editor, perfect for beginners.

#### Basic Usage
```bash
nano filename.txt                      # Edit or create file
nano                                   # Start with empty file
nano +25 file.txt                      # Open file at line 25
nano -w file.txt                       # Disable word wrapping
```

#### Essential Nano Commands
All nano commands use `Ctrl` (shown as `^` in nano):

```bash
# File Operations
Ctrl+O                                 # Save file (WriteOut)
Ctrl+X                                 # Exit nano
Ctrl+R                                 # Read/Insert another file

# Navigation
Ctrl+A                                 # Go to beginning of line
Ctrl+E                                 # Go to end of line
Ctrl+Y                                 # Go up one page
Ctrl+V                                 # Go down one page
Ctrl+_                                 # Go to specific line number

# Editing
Ctrl+K                                 # Cut entire line
Ctrl+U                                 # Paste (Uncut)
Ctrl+6                                 # Mark text for selection
Alt+6                                  # Copy marked text
Ctrl+C                                 # Show cursor position

# Search and Replace
Ctrl+W                                 # Search (Where is)
Ctrl+\                                 # Search and replace
Alt+W                                  # Find next occurrence
Alt+Q                                  # Find previous occurrence
```

#### Nano Configuration
Create `~/.nanorc` for custom settings:
```bash
# Enable syntax highlighting
include "/usr/share/nano/*.nanorc"

# Show line numbers
set linenumbers

# Enable mouse support
set mouse

# Set tab size
set tabsize 4

# Enable auto-indentation
set autoindent

# Show whitespace
set whitespace
```

### `vim` - Powerful Advanced Editor

Vim is a highly powerful but complex editor with a steep learning curve.

#### Vim Modes
Vim operates in different modes:
- **Normal Mode**: Default mode for navigation and commands
- **Insert Mode**: For typing text
- **Visual Mode**: For selecting text
- **Command Mode**: For running commands

#### Basic Vim Usage
```bash
vim filename.txt                       # Edit or create file
vim +25 file.txt                       # Open at line 25
vim -R file.txt                        # Open in read-only mode
```

#### Essential Vim Commands

##### Mode Switching
```bash
i                                      # Enter Insert mode (before cursor)
a                                      # Enter Insert mode (after cursor)
I                                      # Insert at beginning of line
A                                      # Insert at end of line
o                                      # Open new line below and insert
O                                      # Open new line above and insert
Esc                                    # Return to Normal mode
```

##### Navigation (Normal Mode)
```bash
h, j, k, l                            # Left, Down, Up, Right
w                                      # Move forward by word
b                                      # Move backward by word
0                                      # Go to beginning of line
$                                      # Go to end of line
gg                                     # Go to first line
G                                      # Go to last line
25G                                    # Go to line 25
Ctrl+f                                 # Page forward
Ctrl+b                                 # Page backward
```

##### Editing (Normal Mode)
```bash
x                                      # Delete character under cursor
dd                                     # Delete entire line
dw                                     # Delete word
d$                                     # Delete to end of line
yy                                     # Copy (yank) entire line
yw                                     # Copy word
p                                      # Paste after cursor
P                                      # Paste before cursor
u                                      # Undo
Ctrl+r                                 # Redo
```

##### Search and Replace (Normal Mode)
```bash
/pattern                               # Search forward for pattern
?pattern                               # Search backward for pattern
n                                      # Next search result
N                                      # Previous search result
:%s/old/new/g                          # Replace all occurrences in file
:%s/old/new/gc                         # Replace with confirmation
```

##### File Operations (Command Mode)
```bash
:w                                     # Save file
:q                                     # Quit
:wq                                    # Save and quit
:q!                                    # Quit without saving
:w filename                            # Save as filename
:e filename                            # Open another file
:r filename                            # Insert contents of another file
```

#### Vim Configuration
Create `~/.vimrc` for custom settings:
```bash
# Enable syntax highlighting
syntax on

# Show line numbers
set number

# Enable mouse support
set mouse=a

# Set tab settings
set tabstop=4
set shiftwidth=4
set expandtab

# Enable auto-indentation
set autoindent

# Show matching brackets
set showmatch

# Highlight search results
set hlsearch

# Case-insensitive search
set ignorecase
set smartcase
```

#### Vim vs Nano Comparison

| Feature | Nano | Vim |
|---------|------|-----|
| Learning Curve | Easy | Steep |
| Best For | Quick edits, beginners | Complex editing, power users |
| Interface | Always shows shortcuts | Modal interface |
| Configuration | Simple | Highly customizable |
| Speed | Slower for complex tasks | Very fast once learned |
| Availability | Usually installed | May need installation |

## Finding Things

### `find` - Search for Files and Directories
```bash
# Basic syntax: find [path] [criteria] [action]

# Find by name
find . -name "*.txt"                   # Find all .txt files in current directory
find /home -name "config"              # Find files named "config" in /home
find . -iname "*.TXT"                  # Case-insensitive search

# Find by type
find . -type f                         # Find only files
find . -type d                         # Find only directories
find . -type l                         # Find only symbolic links

# Find by size
find . -size +100M                     # Files larger than 100MB
find . -size -1M                       # Files smaller than 1MB
find . -empty                          # Empty files

# Find by time
find . -mtime -7                       # Modified in last 7 days
find . -mtime +30                      # Modified more than 30 days ago
find . -atime -1                       # Accessed in last day

# Find by permissions
find . -perm 755                       # Exact permissions
find . -perm +x                        # Executable files

# Combine criteria
find . -name "*.log" -size +10M        # Large log files
find . -type f -name "*.tmp" -delete   # Find and delete temp files
```

### `grep` - Search Within Files
```bash
# Basic search
grep "error" logfile.log               # Find lines containing "error"
grep "Error" logfile.log               # Case-sensitive

# Useful options
grep -i "error" logfile.log            # Case-insensitive
grep -n "error" logfile.log            # Show line numbers
grep -v "error" logfile.log            # Show lines NOT containing "error"
grep -c "error" logfile.log            # Count matches

# Recursive search
grep -r "functionName" src/            # Search in all files under src/
grep -r "TODO" .                       # Find all TODO comments

# Multiple patterns
grep -E "error|warning" logfile.log    # Search for "error" OR "warning"
grep "error.*critical" logfile.log     # Search for "error" followed by "critical"

# Search multiple files
grep "pattern" *.txt                   # Search in all .txt files
grep "pattern" file1.txt file2.txt     # Search in specific files
```

### `which` and `whereis` - Locate Commands
```bash
which python                           # Find path to python executable
which -a python                        # Find all python executables in PATH

whereis python                         # Find binary, source, and manual locations
whereis -b python                      # Find only binary
whereis -m python                      # Find only manual
```

## File Transfer Commands

### `scp` - Secure Copy over SSH

SCP (Secure Copy Protocol) allows you to securely transfer files and directories between local and remote systems over SSH. It's one of the most reliable ways to transfer files to servers.

#### Basic SCP Syntax
```bash
scp [options] source destination
```

#### Transferring Files TO Server

##### Single File Transfer
```bash
# Copy local file to remote server
scp localfile.txt username@server.com:/home/username/
scp document.pdf user@192.168.1.100:/var/www/html/
scp config.json root@myserver.com:/etc/myapp/

# Copy to specific directory with new name
scp localfile.txt username@server.com:/home/username/newname.txt
```

##### Multiple Files Transfer
```bash
# Copy multiple files at once
scp file1.txt file2.txt file3.txt username@server.com:/home/username/
scp *.txt username@server.com:/backup/textfiles/
scp *.jpg *.png username@server.com:/var/www/images/

# Using wildcards and patterns
scp backup_*.sql username@server.com:/database/backups/
scp log_2024*.log username@server.com:/var/log/myapp/
```

#### Transferring Directories TO Server

##### Basic Directory Transfer
```bash
# Copy entire directory recursively (-r flag is REQUIRED for directories)
scp -r local_directory/ username@server.com:/path/to/destination/
scp -r website/ username@server.com:/var/www/
scp -r project/src/ username@server.com:/home/username/code/
```

##### Directory Transfer Examples
```bash
# Copy entire project to server
scp -r ./myproject username@webserver:/var/www/html/

# Backup local directory to server
scp -r ~/Documents/important_files/ username@backupserver:/backups/$(date +%Y%m%d)/

# Deploy application to server
scp -r ./dist/ username@prodserver:/var/www/myapp/

# Copy configuration directory
scp -r ~/.ssh/ username@newserver:/home/username/
```

#### Transferring Files FROM Server (Download)

```bash
# Download single file from server
scp username@server.com:/var/log/app.log ./

# Download multiple files
scp username@server.com:"/var/log/*.log" ./logs/

# Download entire directory
scp -r username@server.com:/var/www/backup/ ./server_backup/

# Download with preserved directory structure
scp -r username@server.com:/etc/nginx/ ./nginx_config_backup/
```

#### Advanced SCP Options

##### Using Specific SSH Port
```bash
# If server uses non-standard SSH port
scp -P 2222 file.txt username@server.com:/path/
scp -P 8022 -r directory/ username@server.com:/path/
```

##### Compression for Large Files
```bash
# Enable compression (-C flag) - great for large files or slow connections
scp -C largefile.zip username@server.com:/path/
scp -r -C large_directory/ username@server.com:/path/
```

##### Preserving File Attributes
```bash
# Preserve timestamps, permissions, and modes (-p flag)
scp -p important_file.txt username@server.com:/path/
scp -r -p website/ username@server.com:/var/www/
```

##### Verbose Output
```bash
# See detailed transfer progress (-v flag)
scp -v file.txt username@server.com:/path/
scp -r -v -C project/ username@server.com:/deployments/
```

##### Using SSH Keys
```bash
# Specify SSH private key (-i flag)
scp -i ~/.ssh/id_rsa file.txt username@server.com:/path/
scp -i /path/to/private_key -r directory/ username@server.com:/path/
```

### `rsync` - Advanced File Synchronization
```bash
# Basic sync (better than scp for large transfers)
rsync -av source/ username@server:/destination/

# Sync with progress
rsync -av --progress source/ username@server:/destination/

# Sync with compression
rsync -avz source/ username@server:/destination/

# Dry run (see what would be transferred)
rsync -av --dry-run source/ username@server:/destination/

# Exclude files
rsync -av --exclude='*.log' source/ username@server:/destination/

# Sync and delete files that don't exist in source
rsync -av --delete source/ username@server:/destination/
```

### `wget` and `curl` - Download from Internet
```bash
# wget - simple downloads
wget https://example.com/file.zip
wget -O newname.zip https://example.com/file.zip    # Save with different name
wget -c https://example.com/largefile.zip           # Continue interrupted download

# curl - more versatile
curl -O https://example.com/file.zip                # Save with original name
curl -o newname.zip https://example.com/file.zip    # Save with new name
curl -L https://example.com/redirect-url            # Follow redirects
```

## System Information & Management

### System Information Commands

#### `top` - Real-time System Monitor
```bash
top                                    # Dynamic process and system info viewer

# Key commands in top:
# q - Quit
# h - Help
# k - Kill process (enter PID)
# M - Sort by memory usage
# P - Sort by CPU usage
# 1 - Show individual CPU cores
# z - Color/monochrome toggle
```

#### `htop` - Enhanced System Monitor (if installed)
```bash
htop                                   # Better version of top

# Features:
# - Color-coded display
# - Mouse support
# - Tree view of processes
# - Easy process killing
# - Scrollable process list
```

#### `df` - Disk Space
```bash
df -h                                  # Human-readable disk space
df -h /                                # Disk space for root partition
df -i                                  # Show inode usage
```

#### `du` - Directory Usage
```bash
du -h                                  # Disk usage of current directory
du -sh *                               # Size of each item in current directory
du -sh /var/log                        # Total size of /var/log
du -ah                                 # All files with human-readable sizes
du -d 1                                # Limit depth to 1 level
```

#### `free` - Memory Usage
```bash
free -h                                # Human-readable memory info
free -h -s 5                           # Update every 5 seconds
```

#### System and Hardware Information
```bash
# System information
uname -a                               # All system info
uname -r                               # Kernel version
uname -m                               # Architecture

# CPU information
lscpu                                  # Detailed CPU info
cat /proc/cpuinfo                      # CPU details from proc filesystem

# Memory information
cat /proc/meminfo                      # Detailed memory info

# Disk and block devices
lsblk                                  # List block devices
fdisk -l                               # List disk partitions (requires sudo)

# PCI devices
lspci                                  # List PCI devices

# USB devices
lsusb                                  # List USB devices

# Network interfaces
ip addr show                           # Show network interfaces (modern)
ifconfig                               # Show network interfaces (older)
```

### User and Permission Management

#### `sudo` - Execute as Superuser
```bash
sudo command                           # Run single command as root
sudo -u username command               # Run command as specific user
sudo -i                                # Switch to root shell
sudo -l                                # List allowed commands

# Edit sudoers file (CAREFUL!)
sudo visudo                            # Safely edit sudo permissions
```

#### `su` - Switch User
```bash
su -                                   # Switch to root with root's environment
su - username                          # Switch to specific user
su username                            # Switch user without changing environment
exit                                   # Return to previous user
```

#### User Information
```bash
who                                    # Show logged-in users
w                                      # Show logged-in users with activity
last                                   # Show login history
id                                     # Current user and group IDs
groups                                 # Show user's groups
groups username                        # Show another user's groups
```

## Shell Features & Utilities

### `echo` - Display Text
```bash
echo "Hello World"                     # Display text
echo $HOME                             # Display environment variable
echo -e "Line 1\nLine 2"               # Enable interpretation of backslash escapes
echo -n "No newline"                   # Don't add trailing newline
echo "Today is $(date)"                # Command substitution
echo "File count: $(ls | wc -l)"       # Count files in directory
```

#### Advanced Echo Usage
```bash
# Output to file
echo "content" > file.txt              # Overwrite file
echo "more content" >> file.txt        # Append to file

# Colors (if terminal supports)
echo -e "\033[31mRed text\033[0m"      # Red text
echo -e "\033[32mGreen text\033[0m"    # Green text
echo -e "\033[33mYellow text\033[0m"   # Yellow text
echo -e "\033[34mBlue text\033[0m"     # Blue text
```

### `alias` - Create Command Shortcuts
```bash
# Create aliases
alias ll='ls -la'                      # Long listing
alias la='ls -A'                       # Show hidden files
alias ..='cd ..'                       # Go up one directory
alias ...='cd ../..'                   # Go up two directories
alias grep='grep --color=auto'         # Colorize grep output

# View aliases
alias                                  # Show all aliases
alias ll                               # Show specific alias

# Remove alias
unalias ll                             # Remove specific alias
unalias -a                             # Remove all aliases
```

#### Permanent Aliases
Add to `~/.bashrc` or `~/.bash_profile`:
```bash
# Navigation
alias ll='ls -la'
alias la='ls -A'
alias l='ls -CF'
alias ..='cd ..'
alias ...='cd ../..'

# Safety
alias rm='rm -i'
alias cp='cp -i'
alias mv='mv -i'

# Shortcuts
alias h='history'
alias c='clear'
alias q='exit'

# Git shortcuts
alias gs='git status'
alias ga='git add'
alias gc='git commit'
alias gp='git push'

# System shortcuts
alias ports='netstat -tuln'
alias psg='ps aux | grep'
alias myip='curl ifconfig.me'
```

### `history` - Command History
```bash
history                                # Show command history
history 10                             # Show last 10 commands
history | grep "pattern"               # Search command history

# History navigation
!!                                     # Repeat last command
!n                                     # Repeat command number n
!string                                # Repeat last command starting with string
^old^new                               # Replace 'old' with 'new' in last command

# Clear history
history -c                             # Clear current session history
history -w                             # Write current history to file
> ~/.bash_history                      # Clear history file
```

#### History Configuration
Add to `~/.bashrc`:
```bash
# History size
HISTSIZE=1000                          # Commands in memory
HISTFILESIZE=2000                      # Commands in history file

# History options
HISTCONTROL=ignoredups:ignorespace     # Ignore duplicates and commands starting with space
shopt -s histappend                    # Append to history file, don't overwrite
```

### `clear` - Clear Terminal Screen
```bash
clear                                  # Clear screen
# Or use Ctrl+L keyboard shortcut
```

### `exit` - Exit Shell/Terminal
```bash
exit                                   # Exit current shell/terminal
exit 0                                 # Exit with success code
exit 1                                 # Exit with error code
# Or use Ctrl+D keyboard shortcut
```

## Additional Useful Commands

### File Information and Permissions

#### `file` - Determine File Type
```bash
file document.pdf                      # Output: PDF document
file script.sh                         # Output: Bourne-Again shell script
file *                                 # Check all files in directory
file -i file.txt                       # Show MIME type
```

#### `stat` - Detailed File Information
```bash
stat file.txt                          # Shows size, permissions, timestamps, etc.
stat -c '%n %s' *                      # Show name and size of all files
```

#### `chmod` - Change Permissions
```bash
chmod 755 script.sh                    # rwxr-xr-x
chmod +x script.sh                     # Add execute permission
chmod -w file.txt                      # Remove write permission
chmod u+w,g-r,o-r file.txt            # Complex permission changes

# Recursive permission changes
chmod -R 755 directory/                # Apply to directory and all contents
```

#### `chown` - Change Ownership (requires sudo)
```bash
sudo chown user:group file.txt
sudo chown -R user:group directory/    # Recursive
sudo chown user file.txt               # Change owner only
sudo chown :group file.txt             # Change group only
```

### Process Management

#### `ps` - Show Running Processes
```bash
ps                                     # Your processes
ps aux                                 # All processes (detailed)
ps aux | grep python                   # Find Python processes
ps -ef                                 # All processes (different format)
ps -u username                         # Processes for specific user
```

#### Process Control
```bash
# Background and foreground jobs
command &                              # Run command in background
jobs                                   # List background jobs
fg %1                                  # Bring job 1 to foreground
bg %1                                  # Send job 1 to background

# Process signals
kill PID                               # Terminate process (SIGTERM)
kill -9 PID                            # Force kill process (SIGKILL)
kill -15 PID                           # Graceful termination (SIGTERM)
killall processname                    # Kill all processes by name

# Process priority
nice -n 10 command                     # Start command with lower priority
renice +5 PID                          # Change priority of running process
```

### Text Processing

#### `wc` - Word Count
```bash
wc file.txt                            # Lines, words, characters
wc -l file.txt                         # Line count only
wc -w file.txt                         # Word count only
wc -c file.txt                         # Character count
wc -m file.txt                         # Character count (multibyte aware)
```

#### `sort` - Sort Lines
```bash
sort file.txt                          # Alphabetical sort
sort -n numbers.txt                    # Numerical sort
sort -r file.txt                       # Reverse sort
sort -u file.txt                       # Remove duplicates
sort -k2 file.txt                      # Sort by second field
