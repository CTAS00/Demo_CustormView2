/**
 * Created by koudai_nick on 2018/4/20.
 * 时间复杂度  跟执行次数成正比的
 * 执行次数越多 时间越长
 *
 * 推导大O阶
 推导大O阶，我们可以按照如下的规则来进行推导，得到的结果就是大O表示法：
 1.用常数1来取代运行时间中所有加法常数。
 2.修改后的运行次数函数中，只保留最高阶项
 3.如果最高阶项存在且不是1，则去除与这个项相乘的常数。
 */

// 算法花费的时间跟算法中语句的执行次数成正比例
public class placeholder {


    public static void main(String args[]){

        // 常数阶的推导

    }
    // 时间复杂度是O(1)
    private void constant(){
        int sum = 0,n = 100; //执行一次
        sum = (1+n)*n/2; //执行一次
        System.out.println (sum); //执行一次

    }
    // 线性阶
    // 代码循环了n次 所以说时间复杂度是O(n)
    private void lin(int n){
        for(int i=0;i<n;i++){
            // 代码循环了n次 所以说时间复杂度是O(n)
        }
    }
    // 对数阶
    //
    private void duishujie(int n){
        int number=1;
        // 随着number每次乘以2以后,都会越来越接近n
        while(number<n){
            number=number*2;
        }
    }
    // 平方阶
    private void pingfangjie(int n){
        // 外层循环n次 内层循环n次
        // 时间复杂度是O(n2)
        for(int i=0;i<n;i++){
            for(int j=0;j<n;i++){
            }
        }

        /**
         * 需要注意的是内循环中int j=i，而不是int j=0。当i=0时，内循环执行了n次；i=1时内循环执行了n-1次，当i=n-1时执行了1次，我们可以推算出总的执行次数为：

         n+(n-1)+(n-2)+(n-3)+……+1
         =(n+1)+[(n-1)+2]+[(n-2)+3]+[(n-3)+4]+……
         =(n+1)+(n+1)+(n+1)+(n+1)+……
         =(n+1)n/2
         =n(n+1)/2
         =n²/2+n/2

         只保留最高阶 n²/2  去掉常数 则去掉1/2 剩余就是n²
         */
        for(int i=0;i<n;i++){
            for(int j=i;j<n;i++){
            }
        }

    }
}
