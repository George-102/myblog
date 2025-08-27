// 博客系统的JavaScript功能
document.addEventListener('DOMContentLoaded', function() {
    console.log('博客系统加载完成');

    // 平滑滚动到顶部
    const scrollToTop = function() {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    };

    // 显示/隐藏返回顶部按钮
    const scrollButton = document.createElement('button');
    scrollButton.innerHTML = '↑';
    scrollButton.style.cssText = `
        position: fixed;
        bottom: 30px;
        right: 30px;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        background: #3498db;
        color: white;
        border: none;
        font-size: 20px;
        cursor: pointer;
        opacity: 0;
        transition: opacity 0.3s;
        z-index: 1000;
        box-shadow: 0 2px 10px rgba(0,0,0,0.2);
    `;
    document.body.appendChild(scrollButton);

    window.addEventListener('scroll', function() {
        if (window.pageYOffset > 300) {
            scrollButton.style.opacity = '1';
        } else {
            scrollButton.style.opacity = '0';
        }
    });

    scrollButton.addEventListener('click', scrollToTop);

    // 文章卡片悬停效果增强
    const articles = document.querySelectorAll('.post');
    articles.forEach(article => {
        article.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-5px)';
            this.style.boxShadow = '0 8px 25px rgba(0,0,0,0.15)';
        });

        article.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
            this.style.boxShadow = '0 4px 15px rgba(0,0,0,0.1)';
        });
    });

    // 表单验证
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const inputs = this.querySelectorAll('input[required], textarea[required]');
            let isValid = true;

            inputs.forEach(input => {
                if (!input.value.trim()) {
                    input.style.borderColor = '#e74c3c';
                    isValid = false;

                    // 添加错误提示
                    if (!input.nextElementSibling || !input.nextElementSibling.classList.contains('error-message')) {
                        const errorMsg = document.createElement('div');
                        errorMsg.className = 'error-message';
                        errorMsg.style.cssText = 'color: #e74c3c; font-size: 12px; margin-top: 5px;';
                        errorMsg.textContent = '此字段为必填项';
                        input.parentNode.appendChild(errorMsg);
                    }
                } else {
                    input.style.borderColor = '#ddd';
                    const errorMsg = input.nextElementSibling;
                    if (errorMsg && errorMsg.classList.contains('error-message')) {
                        errorMsg.remove();
                    }
                }
            });

            if (!isValid) {
                e.preventDefault();

                // 显示总体错误提示
                let existingError = this.querySelector('.form-error');
                if (!existingError) {
                    const formError = document.createElement('div');
                    formError.className = 'form-error';
                    formError.style.cssText = 'color: #e74c3c; background: #ffecec; padding: 10px; border-radius: 5px; margin-bottom: 15px;';
                    formError.textContent = '请填写所有必填字段';
                    this.insertBefore(formError, this.firstChild);
                }
            }
        });
    });

    // 实时字数统计
    const contentTextarea = document.getElementById('content');
    if (contentTextarea) {
        const charCount = document.createElement('div');
        charCount.style.cssText = 'text-align: right; color: #666; font-size: 12px; margin-top: 5px;';
        contentTextarea.parentNode.appendChild(charCount);

        const updateCharCount = function() {
            const length = contentTextarea.value.length;
            charCount.textContent = `字数: ${length}`;

            if (length > 5000) {
                charCount.style.color = '#e74c3c';
            } else if (length > 3000) {
                charCount.style.color = '#f39c12';
            } else {
                charCount.style.color = '#666';
            }
        };

        contentTextarea.addEventListener('input', updateCharCount);
        updateCharCount(); // 初始化
    }

    // 页面加载动画
    const mainContent = document.querySelector('.container') || document.body;
    mainContent.style.opacity = '0';
    mainContent.style.transition = 'opacity 0.5s ease-in';

    setTimeout(() => {
        mainContent.style.opacity = '1';
    }, 100);

    // 复制文章链接功能
    const articleLinks = document.querySelectorAll('.post h2 a');
    articleLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            // 如果是Ctrl+点击或中键点击，在新标签打开
            if (e.ctrlKey || e.metaKey || e.button === 1) {
                return;
            }

            e.preventDefault();
            const url = this.href;

            // 添加页面过渡动画
            document.body.style.opacity = '0.7';
            document.body.style.transition = 'opacity 0.3s';

            setTimeout(() => {
                window.location.href = url;
            }, 300);
        });
    });

    // 键盘快捷键
    document.addEventListener('keydown', function(e) {
        // ESC键返回首页
        if (e.key === 'Escape') {
            if (window.location.pathname !== '/') {
                window.location.href = '/';
            }
        }

        // Ctrl+Enter 提交表单 (在后台页面)
        if (e.ctrlKey && e.key === 'Enter') {
            const form = document.querySelector('form');
            if (form) {
                form.submit();
            }
        }
    });

    console.log('所有JavaScript功能已初始化');
});

// 工具函数
const BlogUtils = {
    // 格式化日期
    formatDate: function(date) {
        return new Date(date).toLocaleDateString('zh-CN', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    },

    // 截断文本
    truncateText: function(text, maxLength) {
        if (text.length <= maxLength) return text;
        return text.substring(0, maxLength) + '...';
    },

    // 显示消息提示
    showMessage: function(message, type = 'info') {
        const messageDiv = document.createElement('div');
        messageDiv.textContent = message;
        messageDiv.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 15px 20px;
            border-radius: 5px;
            color: white;
            z-index: 2000;
            transition: all 0.3s;
        `;

        const colors = {
            success: '#2ecc71',
            error: '#e74c3c',
            warning: '#f39c12',
            info: '#3498db'
        };

        messageDiv.style.background = colors[type] || colors.info;

        document.body.appendChild(messageDiv);

        setTimeout(() => {
            messageDiv.style.transform = 'translateX(100%)';
            setTimeout(() => messageDiv.remove(), 300);
        }, 3000);
    }
};