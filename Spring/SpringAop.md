# SpringAop

### AOP相关术语

**Joinpoint**
Joinpoint是连接点，连接点是指那些被拦截到的点，在Spring中，这些点指的是方法，因为spring只支持方法类型的连接点。
往往对于Proxy会代理接口中的所有方法，这里接口里的所有方法就都是Joinpoint

**Pointcut**
Pointcut是切入点，指我们要对哪些Joinpoint进行拦截定义. 连接点不一定是切入点。所有的切入切都是连接点。  
可能并不是接口中所有的方法都是切入点，只有那些真正被处理的接口方法才是真正的pointcut

**Advice**
Advice即通知/增强, 指拦截到Joinpoint之后要做的事情。类型分为：前置通知、后置通知、异常通知、最终通知、环绕通知。
比如：  

```java
public IAccountService getAccountService() {
    IAccountService as = (IAccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(), new InvocationHandler() {
        @Override
        // 整个invoke方法在执行就是环绕通知
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            /**
             * 添加事务的支持
             */
            Object returnValue = null;
            try {
                transactionManager.beginTransaction(); // 前置通知
                returnValue = method.invoke(accountService, args); // 业务核心代码，在环绕通知中有明确的切入点方法调用
                transactionManager.commit(); // 后置通知
                return returnValue;
            } catch (Exception ex) {
                transactionManager.rollback(); // 异常通知
                throw new RuntimeException(ex);
            } finally {
                transactionManager.release(); // 最终通知
            }
        }
    });
    return as;
}
```

invoke方法具有拦截功能，而拦截要做的事情就是`transactionManager.beginTransaction()/commit()/rollback()/release`, 因此transactionManager就是通知。 

**Introducton**
Introduction指引介，引介是一种特殊类型的通知，在不修改类代码的前提下，Introduction可以在运行期为类动态地添加一些方法或Field.

**Target**
被代理的对象，在上面的代码中`accountService`就是被代理对象。  

**Weaving**
weave有织入，编排, 迂回行进的意思，是指增强目标对象、创建新的代理对象的过程。比如：  

```java
public class BeanFactory {
	  // 原有的这个service无法实现对事务的支持，于是通过动态代理, getAccountService()返回了一个新的代理对象，这个代理对象增强了原来的service对象，加入了事务的支持，那么加入事务支持的过程就是织入Weaving
    private IAccountService accountService;
    private TransactionManager transactionManager;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public IAccountService getAccountService() {
        IAccountService as = (IAccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /**
                 * 添加事务的支持
                 */
                Object returnValue = null;
                try {
                    transactionManager.beginTransaction();
                    returnValue = method.invoke(accountService, args);
                    transactionManager.commit();
                    return returnValue;
                } catch (Exception ex) {
                    transactionManager.rollback();
                    throw new RuntimeException(ex);
                } finally {
                    transactionManager.release();
                }
            }
        });
        return as;
    }
}
```

spring采用动态代理织入，而AspectJ采用编译期织入和类装载期织入

**Proxy**  
代理，一个类被AOP织入增强后，就产生一个结果代理类，在上面的示例中，accountService是Target, Proxy.newProxyInstance返回的就是Proxy 

**Aspect**  
切面，指切入点和通知（引介）的结合  

### Spring中的AOP要明确的事 

**开发阶段** 
编写核心业务代码、把公用代码抽取出来制作通知、在配置文件中声明切入点与通知间的关系，即切面

**运行阶段**  
由spring框架完成，spring框架监控切入点方法的执行，一旦监控到切入点方法被执行，使用代理机制动态创建目标对象的代理对象，根据通知类别，在代理对象的对应位置，将通知对应的功能织入，完成增强的代码逻辑。 

