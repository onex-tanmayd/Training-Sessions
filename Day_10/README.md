
# 📦 Compressing and Archiving in Linux

## 1. Archiving vs Compressing
- **Archiving**: Combining multiple files/folders into **one file** without reducing size.  
  Example: `tar`  
- **Compressing**: Reducing the file size using compression algorithms.  
  Example: `gzip`, `xz`, `zip`

---

## 2. Common Tools

| Tool      | Purpose | Speed | Compression |
|-----------|---------|-------|-------------|
| `tar.gz`  | Archive + gzip compression | Fast | Good |
| `tar.xz`  | Archive + xz compression | Slower | Very High |
| `zip`     | Archive + compression | Medium | Medium |

---

## 3. Examples

### 3.1 Using `zip`
```bash
zip archive.zip file1.txt file2.txt
zip -r project.zip my_project/
unzip archive.zip
````

---

### 3.2 Using `gzip`

```bash
gzip file.txt      # Creates file.txt.gz
gunzip file.txt.gz
```

---

### 3.3 Using `tar` with `gzip` (`tar.gz`)

```bash
tar -czvf archive.tar.gz file1.txt file2.txt   # Create
tar -xzvf archive.tar.gz                       # Extract
```

---

### 3.4 Using `tar` with `xz` (`tar.xz`)

```bash
tar -cJvf archive.tar.xz file1.txt file2.txt   # Create
tar -xJvf archive.tar.xz                       # Extract
```

---

## 4. Speed & Compression Ratio Comparison

Tested on a **1 GB mixed file dataset**:

|Format|Time to Compress|Time to Extract|Final Size|Compression Ratio|
|---|---|---|---|---|
|`.tar.gz`|~10 seconds|~6 seconds|450 MB|~55% smaller|
|`.tar.xz`|~40 seconds|~15 seconds|320 MB|~68% smaller|
|`.zip`|~15 seconds|~8 seconds|500 MB|~50% smaller|

---

## 5. Visual Comparison (Speed vs Compression)

<img width="1200" height="800" alt="compression_comparison" src="https://github.com/user-attachments/assets/4f592ef7-20cb-48a5-a21a-9ca55113a848" />



**Interpretation:**

- `tar.gz`: Good balance — fairly quick, good compression.
    
- `tar.xz`: Best compression, much slower.
    
- `zip`: Widely supported, moderate in both.
    

---

## 6. Summary Table

|Format|Command to Create|Command to Extract|
|---|---|---|
|`.zip`|`zip archive.zip files...`|`unzip archive.zip`|
|`.gz`|`gzip file`|`gunzip file.gz`|
|`.tar.gz`|`tar -czvf file.tar.gz files...`|`tar -xzvf file.tar.gz`|
|`.tar.xz`|`tar -cJvf file.tar.xz files...`|`tar -xJvf file.tar.xz`|

---



