document.addEventListener('DOMContentLoaded', function() {
    // Copy to clipboard functionality
    const copyButton = document.getElementById('copyButton');
    const installCode = document.getElementById('installCode');
    
    if (copyButton && installCode) {
        copyButton.addEventListener('click', function() {
            // Create a temporary textarea element to copy the text
            const textarea = document.createElement('textarea');
            textarea.value = installCode.textContent;
            document.body.appendChild(textarea);
            textarea.select();
            
            try {
                // Execute the copy command
                document.execCommand('copy');
                
                // Change button text temporarily to show success
                const originalText = copyButton.innerHTML;
                copyButton.innerHTML = `
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="20 6 9 17 4 12"></polyline>
                    </svg>
                    Copied!
                `;
                
                // Revert button text after a delay
                setTimeout(() => {
                    copyButton.innerHTML = originalText;
                }, 2000);
                
            } catch (err) {
                console.error('Failed to copy text: ', err);
            }
            
            // Clean up
            document.body.removeChild(textarea);
        });
    }
    
    // Smooth scrolling for navigation links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            
            const targetId = this.getAttribute('href');
            if (targetId === '#') return;
            
            const targetElement = document.querySelector(targetId);
            if (targetElement) {
                window.scrollTo({
                    top: targetElement.offsetTop,
                    behavior: 'smooth'
                });
            }
        });
    });
    
    // Terminal animation effect
    const terminalContentItems = document.querySelectorAll('.terminal-content li');
    
    if (terminalContentItems.length > 0) {
        terminalContentItems.forEach((item, index) => {
            item.style.opacity = '0';
            setTimeout(() => {
                item.style.transition = 'opacity 0.5s ease';
                item.style.opacity = '1';
            }, index * 300 + 500);
        });
    }
    
    // Add responsive menu toggle for mobile
    const createMobileMenu = () => {
        if (window.innerWidth <= 576 && !document.querySelector('.mobile-menu-toggle')) {
            const header = document.querySelector('header');
            const nav = document.querySelector('nav');
            
            if (header && nav) {
                // Create mobile menu toggle button
                const toggleButton = document.createElement('button');
                toggleButton.className = 'mobile-menu-toggle';
                toggleButton.innerHTML = `
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <line x1="3" y1="12" x2="21" y2="12"></line>
                        <line x1="3" y1="6" x2="21" y2="6"></line>
                        <line x1="3" y1="18" x2="21" y2="18"></line>
                    </svg>
                `;
                
                // Insert toggle button before the CTA button
                const ctaButton = document.querySelector('.cta-button');
                if (ctaButton) {
                    header.insertBefore(toggleButton, ctaButton);
                } else {
                    header.appendChild(toggleButton);
                }
                
                // Add toggle functionality
                toggleButton.addEventListener('click', () => {
                    nav.classList.toggle('active');
                    toggleButton.classList.toggle('active');
                });
                
                // Add mobile menu styles
                const style = document.createElement('style');
                style.textContent = `
                    .mobile-menu-toggle {
                        display: block;
                        background: none;
                        border: none;
                        color: var(--text-color);
                        padding: 5px;
                    }
                    
                    .mobile-menu-toggle.active svg {
                        color: var(--accent-color);
                    }
                    
                    @media (max-width: 576px) {
                        nav {
                            display: none;
                            position: absolute;
                            top: 100%;
                            left: 0;
                            right: 0;
                            background: var(--white);
                            box-shadow: var(--shadow);
                            padding: 1rem;
                            z-index: 100;
                        }
                        
                        nav.active {
                            display: block;
                        }
                        
                        nav ul {
                            flex-direction: column;
                            gap: 1rem;
                        }
                    }
                `;
                document.head.appendChild(style);
            }
        }
    };
    
    // Call on load and resize
    createMobileMenu();
    window.addEventListener('resize', createMobileMenu);
    
    // Add hover effects to feature cards
    const featureCards = document.querySelectorAll('.feature-card');
    
    featureCards.forEach(card => {
        card.addEventListener('mouseenter', () => {
            const icon = card.querySelector('.feature-icon svg');
            if (icon) {
                icon.style.transition = 'transform 0.3s ease';
                icon.style.transform = 'scale(1.2)';
            }
        });
        
        card.addEventListener('mouseleave', () => {
            const icon = card.querySelector('.feature-icon svg');
            if (icon) {
                icon.style.transform = 'scale(1)';
            }
        });
    });
});