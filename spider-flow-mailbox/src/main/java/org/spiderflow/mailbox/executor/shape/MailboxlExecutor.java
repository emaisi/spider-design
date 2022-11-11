package org.spiderflow.mailbox.executor.shape;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spiderflow.ExpressionEngine;
import org.spiderflow.context.SpiderContext;
import org.spiderflow.executor.ShapeExecutor;
import org.spiderflow.mailbox.service.MailService;
import org.spiderflow.mailbox.utils.MailboxUtils;
import org.spiderflow.model.Shape;
import org.spiderflow.model.SpiderNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailboxlExecutor implements ShapeExecutor {
	/**
	 * 数据源类型
	 */
	public static final String DATASOURCE_ID = "datasourceId";
	/**
	 * 发送地址
	 */
	private static final String MAILBOX_MAIL = "mailboxmail";
	/**
	 * 抄送人地址
	 */
	private static final String MAILBOX_CC = "mailboxcc";
	/**
	 * 暗抄送人地址
	 */
	private static final String MAILBOX_BCC = "mailboxbcc";
	/**
	 * 邮箱抬头
	 */
	private static final String MAILBOX_SUBJECT = "mailboxsubject";
	/**
	 * 邮箱内容
	 */
	private static final String MAILBOX_CONTEXT = "mailboxcontext";
	/**
	 * 附件名称
	 */
	public static final String ENCLOSURE_NAME = "enclosure-name";
	/**
	 * 附件数据
	 */
	public static final String ENCLOSURE_VALUE = "enclosure-value";

	private static Logger logger = LoggerFactory.getLogger(MailboxlExecutor.class);

	@Autowired
	private ExpressionEngine engine;

	@Autowired
	private MailService mailService;

	@Override
	public String supportShape() {
		return "mailboxsendhtml";
	}

	@Override
	public Shape shape() {
		Shape shape = new Shape();
		// web界面上显示的图标
		shape.setImage("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/4QA8RXhpZgAATU0AKgAAAAgAAgESAAMAAAABAAEAAAExAAIAAAAOAAAAJgAAAAB3d3cubWVpdHUuY29tAP/bAEMAAgEBAgEBAgICAgICAgIDBQMDAwMDBgQEAwUHBgcHBwYHBwgJCwkICAoIBwcKDQoKCwwMDAwHCQ4PDQwOCwwMDP/bAEMBAgICAwMDBgMDBgwIBwgMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDP/AABEIAK0AtAMBIgACEQEDEQH/xAAfAAABBQEBAQEBAQAAAAAAAAAAAQIDBAUGBwgJCgv/xAC1EAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrHBFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OXm5+jp6vHy8/T19vf4+fr/xAAfAQADAQEBAQEBAQEBAAAAAAAAAQIDBAUGBwgJCgv/xAC1EQACAQIEBAMEBwUEBAABAncAAQIDEQQFITEGEkFRB2FxEyIygQgUQpGhscEJIzNS8BVictEKFiQ04SXxFxgZGiYnKCkqNTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqCg4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2dri4+Tl5ufo6ery8/T19vf4+fr/2gAMAwEAAhEDEQA/AP38ooooAKKKKACiiigAooooAKxfE3jKPQriOzt4W1DVLhC8NnGwDFenmO3SOMHq5+gDNhTn6v40uNcvbjT9AMbPbuYrrUZF3W9mw+8ijP72YdNoO1D98ggIxp2k2XhLTriZpducz3l7dyjzJiBzJLIcDgfRVHChVAAAIG8P3+s/vNW1a8Zm5+zWEz2dvF7BkIlb3ZnweoVc4qWG11bw9hrG/k1GFetpqD7uP9iYDep7/vPMzgDK9R5F8Q/2tSl41t4ZgheFDg311GT5nvHHkYHu+c/3R1OJ4a/a213S7pf7Ut7PVLYn59qfZ5gP9llG38CvPqOtAH0l4f8AFNt4h86NBJb3Vtj7RazALNb5zjcMkEHBwykq2DtJFaVed+FvF+g/GPTkvNNvJFvLLlSpEV9p5bHUcjaSBkHdG+MENjFb1l4xm0KVLfXPKRWYJFqMa7LeUnosgJPkuTxySjEqA25tgAOmooByKKACiiigAooooAKKKKACiiigAooooAKN1cn498a3mm6va6PpSw/2jdRmZ5ZgTHawg43kD7xJyAOmRzVOHSru5j/0rXtYuX/iKPHbKfoI1BA/4EfrW3srJSk7X+85vrF5OFOLdt3olft/wyZ3GaZNcR20ZaR1jUd2OB+tcanhKxmH75tQufXz9SuZR+TSEfpU9t4J0KGTd/YulNJ/fe1Rn/76IJ/WptT7v7v+CVzVv5V97/8AkTWuviDoNi+2bWtJhb+693GGP4ZzXH6h45/4WFM8KyanYaGrFSLa1uGvNSHp+7QmCI/hI3/TMD5+zsoIbJNtvHHCvpGoQfpVgS5PJP4nrReHZ/f/AMAOWt3X3P8AzObsNVjtLKG3sNF1ZbeBBHFFFYi2SNR0CiQoFHtxXnHxQ+GvxC+L94YJI7HRtFjYGKz+1h95HR5Sv3m7gfdXtk5Y+3K+fapFejmj/L+f6WH7OfWb+SX6pnzjY/sSa27Zuta02NfSNHYj8wK2bH9hu2Xa1x4hum9VjtlUfmWr3dWx0p6tu9aPaW2S+6/53D2N/ik387flY8q8L/skaF4V1SC/t9T12O+tzmOaG5ERX1HC8g9CDwRwQRXZaj8N4dR024hm1DV5zNGyZN7JEDkY5ERQEe2MV0maAc0e1l009El+QfV4ddfVt/m2fPvhP4heNPDHhjXNJ0W203VNd8MzCAaXqdw8MM0RyYmimG4xhlDKFcMoaMcxr1+bPEH/AAXzf4ZfEu48I+Nvgr4p8L61p5Au4bvUUEig9HRVjdXRuqushRhyGI5r6+8e6OvhX45aPqyLtt9bQ6Xd4HDF8eUfqJRGM+jN61iftEfsk+Cf2j9GtbPxh4es9Y/s9zJZzuXhuLUkgsFljZZFRsDcoYBseoBHuZPjMuo1Eszoe0pvdpyUl6WlFPvZ99z5viDL82xFJvKMV7GrHZNRcJddbxlJdrrtscX8Iv8AgsF8G/ipJHHcX2s+F5pSERdWsSFd/QNEZMD/AGnCjA7dK+lvDHi3S/GujQ6jo+pafq2n3GfKurK4S4hkx6OhKn8DXx+v/BKT4M6/Ill9n8TeE75jiNrLVvNhuD6IblZTkj+Bvm4OC4UtWFD/AME1/ih+zHrb698IfiE10yndJpt0otHnQDARsl4JzycCRY1HUEEA19Licr4Uxy/4TMXOhPpGtH3W+3PG/L6yuj4zC55xzlkv+FjAwxNJbyw8vfS7+zny8/pGzPu2ivB/2Z/2xLj4i+Jm8E+PtFfwZ8RLdSVspVaO31UKpZmt95JDBVZ/LLNlAXR5VSQp7xXxGYZfXwVZ0MQrPfdNNPZprRp9GnY/S8pzfC5lh1isJK8dndNOLW8ZRdnGS6ppMKKKK4T0gooooAKKKKAOF+INv/ZPj7TNQYfu9Stn05m7K6nzU/PEg/AVJFc4/wAa0fi3o0useBbtrdd15p+2+thjkyRHfgfUAr/wKuZ03V0v7SG4hb91cIsiH1Vhkfzrep71OMvl+q/P8Dlo+7VnT72kvno/xV/mb8V1nr+lWIpsjrWNFd59qtQ3O0+lYHUasc/NWI7jcfmrLhuQ45/MVYSfAoAtaprNroOk3N9fXVvZ2NlC9xcXFxKscNvEgLO7uxAVVUEkk4ABNfm7+1T/AMF09Q8SeKbrwn+z/pFnqX2fMd14t1iFjap94FraDjKjgrLNncVYCF1wx4//AILwftt6hr3jCz+AfhO5kWFfIuvFDROVa8nl2vbWJ6fIqlJn6hjJEONjA+b/ALCv7GF58dPGVr4R0mRrHS9PjW813VlQEwoTgsAeDLIwKxqegBPKowr9g4T4OwNHL/7azr4bc0YvZR6Sl1bf2Y9dN27L8Y4v42x9bMf7DyP478spLdy6xjfRJfal0s9krvPsvEPxl/ac8UGw1bxz8TviHrN8A8ul6de3FrYKM43/AGK0KQooJwXZQvrjpXr/AIH/AOCOXxS16MXcmh+CfDFxncRqV+n2gn1zaxTAn6tX2L8Tf2kP2e/+CUHw0s9J8Qa7pHg6O6j8+DToIpL3WdYIyPPaKJWmkyVK+dIBGpwu5AABxPgH/gt78L/HLPIfCfxS0fT9u6G81PSbSFbkdtsQumnAI5BeNQR0JrV8X5riLxyDBKNJbNQu2vRWivTX1M/9Tcqw9pcRY5yq9U52S+bvJ+unofMfxF/ZN/aI/Zl8y9tdL8cXljakBtR8FaxLeAZ44hhkS8YY64tyAOtWP2dP+CvHxB8JXCx3GtWPxD0m1by7iy1LEN9bgEggTKolRt2QTOknTGB2+lv2gv8Aguh8E/2e/DGk6tNB448UW2pGVZF0bSEEmnvGEO2YXUsABfedhQuG8t+flrwL47QfDT/grZ4z+z+EPBfin4Z/GpbSa80fxJcmySDUDBFuFtfm3ndmR0UIJArPEduCyBo5OvB5liMZTceIsvXs0tZ8vK4rq2n7y2d3FppbJnHjMrw2Cmp8N5i/aN6Q5uZSejSTXut6qykmm92j7L8P/tMeFf2uvg9JrXhW6kj1LR5Ea7025Cpe6bL1jLqCQVLL8sikoxBGdyso9w0+7t/Emkw31u6yQXSCRGB9e31HQ+4r8GP2aP2jPFXwl+LF1a3UMmg/EDwfPLZalYzrsS8VH8ueCZFOCNy7XUHGQroQQpT9ePgfYW3x9+Gml+L/AAvql9Y6frkfmSRQX89tJbzKSkkUoiZQzo4ZS2PmxkcEV8bxhwnHKpRq0ZOVCb916NptbPa91rF9UmraXPs+DOLqmbKVKtFRrwXvJ3V0n8S0dmm7SXdpp9D1/V/Cy6hBJG0IljkGGVk3K3sR3qtYahqnhT5ZobzUdPXGW2tJcW47n1lXvg5k4ODISFHKTfsr6frv/IY1LVNRz18+7lmP/fUjE1Y8O/sb/Dvw1qsN/D4ds5L6Bg6Tyjc6kdwa+ElyfZb+7/gs/QIup9pL5O/6I0fjH8FPDf7SPgqOG6do7iFvP0vV7GTZeaZOrBllhkHKskiK2OzIp4ZVIyv2Y/jRqHjy21zwr4p8qHx94CuV03XViULDfBlD299CBwIriIrJt4MbF0I+UE52kWNx4A8beILPSZlsZra++2rA4Jtb2C4HmL5iDoQ4mjWRMMBEgO9VEZ4zxJ4ot9B/a58N+KIoH0y+1rSG07VrZ8BTHBcIvmK44mVvtcbbhyBaxqwU5Qe3g17eE8vqO9k5U32duZryU43uv5uV9Hf5fMprCVIZtRVryUKq7xb5E/Nwk1Z/y8y6q30xRRRXgn1gUUUUAFZ3inxZp3gvRZdQ1S6jtLSHALtklmJwqKoyzuxwFRQWYkAAkgVo18s/EG68SeJ/jXrEz+KTpN/4bvpLSwiOmx3VvbwPGjoyo2dsjxyANJgsckAhcKAD03WbzUPijIr6xbyaboKndFo7HMl56NeEEjb/ANO6kr/z0L58tMnwpOdIa+0sn/kGXTJGD1ML/vIj/wB8tj/gNcJ44+KfxF+E3g261xvD+l/EWzslDvB4cEy6kQTjLxMvC85JjjfaASeleb/skftjJ+0R8S9Ua6mghub6EGGyCKht0QEhRgkyBcHL5Y5l6jhR0UfejKn5XXqtfyucuJvCUKy6Oz9Jaf8ApXL8rn1FFeZ796twXm0f0NYiScfKfwqeG7x1rnOo34rnd/8AXqb+1IrCJ57p/LtrdTJM391FGWP5AmsWG73Vznx+v7iP9nv4gPa7vtSeFtVaAjqHFlMVx+OK0pQ55qHdpGdapyU5T7Jv7j8KPBfje7/aJ/ag8VePtU3PdateXetuGO7y3uJTsQeyI5CjsEGOlfqh8EviRpP/AATw/wCCWuufFnULOO61TUYH1iK3c7TqFxLJ9m0623dfLYmNjjO1ZZWA61+T/wCxuyyW3iBl+8EtVH0xLX2p/wAFuvGEmnf8E4f2dPDVmStlq4tL6Tb91vsulRqoP43RP1X2r+jOM8L9Yjg8rhpCc0ml/LCLdvu/I/mjgnFfVpY3Np61KcG03/NOSV/67s+Lvg5Za1+0T8Tde+MPxG1CbxF4k1i+aZLm85DzLgGUL91Y4gFjhRfkiEeFA2Jj9TP2cf8Agk1pfxC+CC6r441nxFo/iLxJarcWUemtCj6LG+GjZ1mjkWSZl5ZXXaocrjcN9fD37G/gK1168+Fvh2RA1nq15plvcL2dZ5ozL+e9/wA6/cw3G6Ru2TwBXgcfZ1XymjQwOXv2bkm21o7KySXZb38kltc+h8Pcjw+b16+OzFe05bJJ6q7u233e1vVvex+AP7RHh3xJ+yx+1JrHwP8Ai0um69ZakEXS9ajtPssOsWs5It5WiB2qHKmM7eYpkZdzgCQfXn/BKiIL+2joWOkem334fucV5r/wdVeHrXTvFPwX8SW4WPVrjT9Xs5JVP7xo7eW0lh5/2WnlI93Nelf8Em7hr/8AbD0WdhhpNGvZGGOhMSn+td9HOKuY8MV69f4vZyu//Ak/xV/mefXyWll3FVDD0PhVWNl2vytfg7fI83/4OD/hRF+z9+2f4H+KmkwrCnjixb+0FTP7+7sTHFKzenmWs0Ccf88mPU5r6r/4Ir/FN/tfjbwW0/mWzLDrtivpnEM5+hH2f9fWvAv+DpLxlaxWHwZ0NXjbUGOsag6A/NHFi0jUkdgzbsevln0Nd1/wRN0q6tfj5qLSK3+g+Djb3Bxx5hntAAfclHP4GvIpTeK4Kkq/2Y6P/DO0fwVvQ9qrFYTjqP1fTnlqv8ULy/F39T9Plf1/OnZqBXx9786eGx0r8WP3A+ef2+dS1z4Yadonjnw9cfZ7ixlNjfI43wXcJy6LKmRlVIkOQQy7vlZTzXleufELxH8XfhZ4f8a6hp0PhzQ21Ix2VkQZLi+U2s6m68zIKws64RdgLdTkKjv638XbkftTeNJvCybv+Fe+F7sHXZ0bH9vX0ZDLYRsOkMRw0zjknCAqfmON+1+62HwOfykVPLvLaOGONQqr1VVVRwAF4AHAGAOK97IZ8+ZYaP8AeUfvdvydj5biul7PJsZP+7KS8mkn+av6s92+Dviefxh8MdGv7qb7RfPbiK7k2hfMnjJjlOFAAzIjHAAArpq89/Z4lNlo2uaR/DpGqOkYz1WWOOct9DLJKPwNehV4crX02Pp4X5VzbhRRRUlBXzj8bdN/sH9om+KriPXNKtr7PrJE7wP+S+R+lfR1eI/tdab9j8R+B9Y+6v2q60iQjv58Pmpn/gdsB9W96AGeA7kxXfykqWTgg4II5BHvwa5T44/sc+CvjnqX9sXFrceH/FkbieDxDorfZb1ZV+68oGFmIIX5jiUAYWRK3PCNx5d7C3+1j8+P613C8igD51Txl8S/2Zomj8eWbePPCFvwvivR4sXVqgyAbyA4xwMljjGf9bMxxXrXgvx9o/xE0KPUtF1G11OxkOPOhY/I2M7XUgNG2OdrgMB2rvLENbsrKzKw6FTyPxrzvxh+yjo2p68+veE7qbwJ4mcfNdaZEv2O85BxPa8RuCcn5QuWO5g5oA6FJGT3qYxW+r20tnefNaXkbQTgDkxuCrj/AL5JrgF8ca58OJo7Px5pcemKziOLWrAtNpN0x6Zb70DH+64GcE/KK7K3u47qFJI3V45FDI6MGVwehBHBHuKA8mfgX8ANEu/g58evGfgLVh5ep6TPcaZOv964sp3icD8N5+gr7N/b48NzftBf8EfvAPiSxX7Re/CPWxYamoGWhtWDWufyfTyfZiexrzb/AILh/s+ah+zt+1VoXxt0G1LaL40ljTUNvEcGqwxbXjY9FFzbIHHdnS5PavWv+Cdv7SfhmKS/8MeJDa6h8N/ipZfZLlbs/wCjxSOjRYl5G1XVmhkOQVYISVCEj+iKuOlmWU0M0wy5qlFxqOK3aScakV52bt3su5/NlHL45Zm+IynFPlp1oypqT2V2pU5Pyulftd9j51/ZK+Jb+H/D/g3X4F8668K3tvI0QPLPayq6p/wJFT/vqv3O8NeLLHxloVjqul3C32matBHd2c6HImikUMjD6gjjr261+Nf7QH/BOL4jfsKfEjVLzwzoevfED4V6hKXS5022N3qOloMkC6gQblkjGQZgohkXBJjY+Wln4a/tj+MrL4V6p4J8F/EHVtL0q8DiSLTHiXUdL3k+YIWkjaa0LknOwIQxLKVYljzcTZJQ4nw9HF5ZWjeO939l23tdprs11fkdXC2eYjhXEVsJmlGVpdl9qN9Veyaae6fY5z/guf8AGK2/bP8A2/PD/wAO/Dtx9u0f4dWz6RfXMZDRpdPKJNQZSO0SpDCc/wDLWJ19M+zfsC/GTwr8Af2hJPF3jPXNO8M+GdH0PUJbq+vJNscI2rtVR953b7qxoGd2IVVLECuB+Bf/AAS9+Ier/DXXL7wD4Vi0m6a1/wBGvPEcxspdVbjCQ70+bAJYErHAWAy+c10fw+/4J4/tBa9oMdv4h+GLabexp5Vw8viHRpra77F18u7ZsEclWUYJIGRXbTp5Jhsunk0sVCL5eVtyinqtXq97tu3R6HFUqZ5isyhnawk5Lm50lGTWjVlor2skr9dz52/aG+Nmqf8ABUH9tLUPirqFjd6b8PdFKad4cs7tQJJbO3d2ijYDKl3laSaYgsFMhiDNtDD9av8Agk78Abr4XfBO88WapC0OqePHiuYUcYaOwjDeQSOxkMkknHVGj7jjxHwF/wAE/vDv7MHgrU/if8fNc0W18I+DbX7fPpNo5mgcJtEcczkL5hZysaW0QbzXZE3sGMbeHfCj/guv8evj8da1i30H4eeCfCUV06aWV0q5u7+ZAx+R5JLnym8tNqs6xKGfIULtYD53M/8AhTwayLh+0oRtzz2i7aqKfXXVvvbV3PpMq/4TMa8+4jvGpK/JDeWujk1000S7X0VkfsYkmP8AA1wnxZ8T6jrt3/wiPh26ls9SvIhLqepRddEtGON6n/n4kwVjXqMM5wFycn4SeNvGS/AnwzceLrezm+IGuW/mNZRQG1iikcl1WRMkoIYinmn+8CByVFdR4X8Ix+FNOkj86S8vLyU3N9eSLiS9nPDOwHQYAVVHCqAo6c/jVal7OpKm2nZtXWqduqfbsfttCt7WlGqk1zJOz0aur2a6PuZ+g+FdP8HeHrPSdLtI7HTdPiENvAnSNRzknuxJLMx5ZiSeSa8//aB0Y+Jb3wLoigt/aHieC4nXsbe1t7i5lz6A+WqfWRR3r1ieHH9K5m50H+0fHq6lIvy6TYvZW/u87xyTkj2WG3Ckf35R6it8vxHsK3tusU2vWzUX8pWZxZthfrWG+rdJuKf+HmTkvnFNerI/g9f/ANn/ABc1a1dstqumRzRr6G2mcSH8RdRD/gNesV4lp122gfGfwzcDCrc3M+mzOTwI5IHcD8ZoYB9SK9trjPSCiiigAr5u/wCCkX7QPh/4P+CfCOkagzSa54m8QW39lxqQDGLd0llmOeqAFIsDndcp2yR9Ik4Ffhf/AMFFP202+KH7QHib4iWky3VlptzH4c8GoDlTbROXmu1GAHDx+aeRuRtQtzn92K+q4T4dea1qnNfkpxbf+J6QXzlr6JnxHHHFkckw9Lkt7SrNJJ/yrWcreUdPWSP1G0F9r/L2OARXoVkftCq394Aj2zXjPwU8Qx+J/h9od/DIsyXFnF84OQ5UbWP4sp/OvZPDLedpsbf3cr06c/4Yr5U+39DUgjzV+3j4qtbx5xV6Ic0APe0ju7aSGaNZIZkMcsbqGSRT1Ug8EH0PFcFqX7P0ejSyXXhG6TRWkYu+mzhpNNmY9Sqj5oSfVOOANuK9DVei/masxpn6CgD5y+OPwi0P49/DLWPh58RtDmj0zxFB5E0DyD5mBDJNa3ABXzY2CuhxlWA3KRlT+O3xv/Zp+IH/AASv8dz2Him1uvE/wj1m7Kad4ms7cmGN2+6JU5+z3BAw8DnEm1miaTa2f6EtR0q21qzktru3hureQfPFKoZW/A/zrg/GXwQS80e8s7aO11bSL6Fre70fVUW4guYWGGizICHQjgxyhlPQsBX0vDnFGKyirelrB7x/Vdn+f3W+X4l4Vwuc0uWr7s1tJfk+6/Lp5/nn+x7/AMFPLjwV4U02x1xpPGPhJUEdhqljMGv7ONeBH85CzKvQK7JJHyNxCqg+zvAf7b3w8+IkEbWHxC0mCR8D7PqV8dNmz/dCXGwsR/s7h6E181/EP/gjJ8JdW8VXmpeC5vF/wb8QTfvLi00G5R9NnfPDy6fdJLGyDkKsDRRj+GuXH/BJHxZpxCp8TfDOuxdMy+F59Lkx/tFby4Vj9EQewr6fF1uE81k69SUsNUe9otxb72Sf4Neep8rg6PF2URWHpxhiaa2vK0ku121+N/KyPuHXfjn4N8JWX2zWPGfhHSrVhu8+/wBbtbeNh67pJAPxzXi3xj/4KzfCv4ZWk0fh9te+JOsKCsVr4dsW+xs2DtLahP5dr5ZPBaOSVgOQh6HyHw3/AMEgtc1aTaPG2lW8nWTyNHeVUHqWM0f9M4rvPDf/AARl0m0lVtc8e6tqq9WTTbGLTmPtulM4/SuejlfCVB81fGyqeUYSjf74v80dVbNuMK/u0MDCn5ynGVvukvyZ8G/tefF/4if8FCvFdjN8Tb600DwLpFz9o0nwPody728cpyoku7nCm4uNrFN6gAKzeUId7hvsb9gr/gni/hafTfGvxA0k6Ppmi+U+heGmt9k0kgIELzQ4GxVbb5cBAdnwWCqu2T6W+EX7Ifw//Z3vY7zRfDFvBq0ILLquoO13eLwQWSSQlYuM58kICOtet+FtHfUZ4tUuFZY0ybKJhgqCCDMw7MwJCj+FSc4LEC8243oQwry/I6XsqbVnJ/E11Ss3a/V3bfkzPKOBa88Wsyz6qqtRO6ivhT6N3SvborJLzRLoOiTRvLqN8oOqXS7XAbcttF1WFT3x1Zv42JPQKBelTI/zxV2QYOarOm1sV+cH6YUZ4dw96ozRY7d84961J1qjdJyaAPLfjPdf8I/btqhO2PR57fVn56pbTR3Dj8VjYfjX0BXjPxW0eHVdPaGdd1veRPBKPVTwR+TGu++CXiKbxX8IPDOoXUnmXtxpsH2s5zicIFlB9xIGH1FAHUUUUUAeBf8ABTb42yfAz9jPxdfWswh1TXIRoWnkOUcS3P7t2QjkPHD50g94xX4I/G+wuNU1HTNOi+W30u0EhUfdeafErOPcxfZ0P/XGv1l/4LveLLi8034Z+EbeRSmoXd5qUsZP3ZI1iggYj3FxOPwNfm3418PQ6t4r1S6iXMNxdyvECOiFztA9guB+Ff0x4X5Cv7BjVtrVlKTflF8iX3qTXqfxz40cTv8A1olQv7tCEYJeckpyf3OKfofo1/wS78cN4x/ZG8MrK2640lP7PfPJPlhQW/FxIfwNfYHgqXfbSJ/dIb8+P6V+ev8AwSO8QNp+leI/DzsflZbuFT0VVYBsfVpufw9a+/vAs+Lrb2kX/A1+C8UZe8Dm+Iwr+zN29G7r8Gj+oODM1WZZFhMcteenG/qlaX/kyZ2Fsu38qvQL0qnajj8qvQDj8K8E+mJ4BlmPpxU6cJ+tQwj92frVigByDC06iigCjrnhyy8SWohvbeOdVOUJyrxn1VhhlPupBrkbz4XXFlfp5N6slmzfM0wxNEPwG1/QfdI4znk12t3erbbV+9JJ9xB1b1P0Hc1EYGlO5m3fhxQBRsrOLTrVYYV8uNeQO5PqT3Pv/Sps1YW1xVXXtW0/wpo8+parfWemafarvmurudYYYh6s7EKo+pqoxcnyx3JlKMYuUnZLdhLbrcRNHJGksbfeSRAyN9QeKuJf7h8y7fXBzmvm34sf8FT/AIS/Dq8Nho97qPjrV2cxR2ugWvnRu+PlxO+2N1J4zEZD14NeO+Kf25/jR8W1K6PY6D8J9Jk24lkjGr6x74EgWJQe6vGjr2Jr6/L+A85xKU50/ZRfWp7v3Rs5v/t2LPgc08T+HsHJ0oVvbTX2aXv/AHyuoJ/4pI+6PEnjHSfB3h+41TWNU07SdLtRme8vrlLe3hH+07kKv4mvP/BH7Vvg/wCK3j638P8AhmfUNcklt5ro6hBZtHYLFHty6yybPOQl0UPAJE3OoLDIr4L8Xw6X4cQ+KvF+pat4s1Szz5Woa9dtqF0rsciO3V/kiyQdojVdoBJbAJH1V/wT0+H+q6R8Krr4ha5p8k2q+Pis8McbbpLDTYy32ZFU/eD7pJiynLiVCVyMD1s44Lw2VZZLG4io5yuoxsuVOT+9tJXk3p0TWqPn8h8RcbnmdQy3B0Y04Jc8225SUF6WjFylaKXvdWn7p9GTr/KqN0nH6VYs9UttXgaS2mjmVG2vtPzRn0YHlT7EA1Hcfd/Kvzg/YDk/iFa+bou4fwP39CP/ANVS/sramJPA2raZyW0XW7uJm/veeVvh+S3YX/gNW/FNv9o0W6X/AKZlv++ef6Vyv7NmonTvih4o01m2RX1jaX8Kf89JUeaKdvwT7IPyoA9sooooA/MH/gt3fzR/tT/D2Lnyl0eNo/8AeN5IG/ktfH/9noyD7vSvuD/gvz4MurB/hl4xtYmWO3lvNKnnP3VlIint1/ER3J/4D7V8H6j4hiGp3Hkjbbu5eIZ/5Ztyn5qRX9jeFWMpz4ew67Jr5qcv80z/AD98cMvrQ4rxUu7i/k4Rt+TXyPuj9iz4f/8ACBfs/wDw98WfdXXvGOqadIwGMx3FmkSA/wCyJ9OQDPdz619e+ELjybuFugDDP06fyrwb4G6e1x/wR30HVrdfMufDMs3iOJgMlfsmtTXEmP8Atmki/QmvbtBmVz+7YNGxyjA8MOxFfy/xpiHWz/GVH/z9mvkpNL8Ej+0PDvCxw/C+X04/8+ab+coqT/Fs9LteF/EVdhPH4Cs7Tp/PhVv7yhq0IDXzJ9kWYj+6/OrHf8arwchh+NTZylAE1UNa1tdLWKNIzcXd0dtvAp+aUjqfZR1LHgfXAPwB+2l/wVq+I/wL+OniTwLpPhTwvpD6HceSl7evNqEl3C6LJDOgBiRC0bo20iQKSVy2M18v67+2l8ZPjprMyz+MPFF3cTRkSW+kf6H+6GRtZLVU3RjPO7I7nJ5r9QyPwnzbH0YYqpOFKnJKSbld2aunaN1t3aPxXiTx0yLKq88HSp1K1WDcWox5VzJ2abk09+0Wfrx40+L/AIP+Clu1x4x8WaBo95OvmMLy8SKSQD+GKInewHYKCT1618//ABS/4LD/AAz8HrJH4asdd8Y3W3MckVv9gtC391nmAlH1ETCvzw0j4YzXDNJqV9a2rSktIsJF3M5PO4lW8s575k3D0rr/AA74J0PSWVo7BbqT/npekXH1GzAjx9UJH96v0LA+EeSYX3sZVnXfaKUI/N6trzjI/Jcz8e+JMc3DL6FPDR7ybqTXptH5SiexeNf+Co3xo+NDSx+C9L0vwlp7OQtxbQLczR+qNc3A8kn/AHY0b0rzHUPhbrXxT1iPVPiB4u1rxNeRkmNHu5J/KBOcLJNkqvYoiKPRq3LbUGkZd7M20BQSc4A6Ae1aVrdY719ZhcBhMvXLltCFHziry+c3eT+9Hw+MzTMM1lzZxiZ1/KTtBelONor7mXPCXhjSvB1u0ek6fa6esi7ZGiU+ZIPRpGJdhnszHFaGs+JbTwrpE2oX0ywWsC5ZsZLHsqjux6ADrWLq/imz8K6TJfX03k28Pfqzt2VR3Y88e3YAmvBfiT8TLz4j6oJJcwWNuT9mtQcrH/tH1Y9z26DiuzL8nq46reTfL1l19Nd3+W78+HNeIKGWUOWCXNb3YrRLzfZfn07npnwf8Jal+27+0roPh11mt9F80zXMcbH/AEKwjw0zlh0kcbUDdN7oOmK/XnTo7bSLCG1tbeK3treNYooo0CpEijCqoHAUAAAe1fHn/BKb4D/8Ku+Dk3jLUINmteNQr229fng09CfKxnkea2ZDjhlEJ7V9TSatg1+B+KfEFPHZr9Rwn8HD3hG2zl9t/erX68t+p/UfgjwrVyzI/wC0sd/vGLtOTe6jb3I/c+a3Tmt0LfiDw9Y69MszeZa3qjal3bSeVOg9N3Rh/ssCPasC/vNY8NBvtVt/blio/wCPmwj23UY9Xg/j+sRzx9yr9xrKwwtI7JHGvLOzbVX8a5nWfjf4f0bcG1SK4k/uWoM+f+BLlfzYV+Yn7Oaum69p/iuwaewu4by3z5btE3KHnKsOqt/ssAfavN/Ad7/wjf7Rfhl2BZtSS+0Tbg4XfELst+H2DHP9+snx98XdL8R3/wBs0/Tbyx1ZRtj1SO4FvcgejqgYSLxja5YY4wK4fV/i9Ponibw5e65daXasuvWEseoSTJa7h9qjEw2MQC7QtKoCcZfGBmjUND7YooHSigDxb/goH+zMf2sP2WPEfha1jjbXo0XUdEZ8DbfQ5aNdzEBRKu+EsfurMx7V+D2qalNYWzLNHNBdWDmC5hmQxyRYbA3KRkMrZRgcY+QY64/pKr8y/wDgsX/wTRurrUdU+MHw+03zvORrjxVpFtH8zkD57+JB1yOZlHJOZMEmQn9b8MeLo4GpLLMTLlhN3i3spbNPylp6Nedz8J8ZeA55nSjnGDhzVKatOK3lDdNd3F39U/Kx7r/wSPmsfjJ/wTH0vQLhvMiY6xot6D83E11cPjH/AFzuE4rX/Zl12fXvgt4Wmuwy3sVhHZ3YbqLiDNvLn38yJ6+Uf+CDH7YXhH4Q6B4p+GfirxFp+ivrWsx6toEl/J5Ud1JLCkMsAlP7tWBghKKzAuZSFDHNfV/wttf+EU+IvxJ8NnAOi+LLq6hQdEgvkiv0wOmN9xKMdtpr47jXA1sNnWI9rFrnk5K+zUnfR9Vrb8D9B8PMyoYvh7C+xknyQjBpbpxXLZro9L69NT2/w9PvsY/9n5TWxA2MVzvhqbNuy+hBzW5BJnmvlT7Uvxttceh4rG8f/EO18AafDuikvtS1CTydP0+E/vr2X0H91V6s54Ue5AOZ8UfivYfCvQ4Zp45b7UtQk+z6ZpsH/HxqEx6KvooyCznhQR1JVTyfgjRL5NWm1zXZk1DxTqSCOQwjMNhFnItoBzhBnk9WOSSepAPFv2w/+CfMn7SF3H40+1abf/EK3tBA9pemSPS541ZnSKMxspV03FVaXej4w4XO8fDvi3TPEHgDXLjw34i0+60O6sH3S6W9utrFE3OJFijAiIbqJEGHHIJBzX6KfHr9vT4Xfs4efD4o8X6eurQZVtH05vt2pbxzsaGPPkk9jMY1P96vgX9q/wD4KS6v+2pdReHfBXw6sobDS3+0R6lqUYutVgj3DLmRSsNlA3AkDM6nAPmjAx+9+GGd5zSjDDYnD8+GW05e64L+638S8lr52Vj+W/Gnhfh+vKeNwmKVPGP4qcfeVR/3lH4JW+09O6u7mNp+oZxzW3Y32a4iwnmsookuZbGS5KAzraTNNHC/OU3lVDEY6puQ5+VmHJ3bDUMkV+8VqKlHmWz+R/MOHxLhLkluvNP8VodlYXtSa944s/B+m/aLyTlgfKiU/vJj6AenqTwPyB4XXPiPD4eRoodtxeYxsz8kZ/2iP/QRz7iuD1XV7rW75rq6maaZ+rN2HoB2HsOKwoZQ60uappH8X/Xc7MVnyoQ5aesvwX9f8Oa3jHxre+ONT+0XTbY48iCBT+7gU+nqT3J5PsAAN79nr4YW/wAXvi5pOjX80lto7SfaNUnRWLRWqYMm3aCQzcRqcYDSKTxmuHSTAz2719f/ALF/7Nfjp/A/9oaT4E1i4uPEBWT+0NSli0uxjt8ZiAaQmZ1bJffFA6kMvJwM+Z4gcSU+H8jnUoe7Ul7lNf3n9r/t1Xl62T3Pa8K+D6vFXElOlibypQ/eVW+sU1aP/bztG3a7Wx9U3/7R2m6TaR2uk6TJ9nt41hhjZlt4oUUAKqqu75QAABxgCuY1n4++INSRzFNb2MSglvs8PRfUs+4j6gitXwt+wl4v11EfxL4y03QY2Hz2vh6w+1XCfS7uwY2/8BB9fT0bwx+wr8NdEaOXUtDfxbdJgmXxHcyaom4dGWCUm3jb/rnEnPPWv4n5nuz/AEaUbKyPmeH4mj4j6m8WmTat40voX8t10mCfVzA/92R4g6Q/9tGQCu38L/s5fE3xmVYaDpXhS2Y4aTXdQWa6QdmFtaeYjj2a4jPt1x9eWVjDplpHb28MdvbwqEjijUKkajgAAcAD0FS0XY+U8A8MfsG2zqj+KfGOv6w3IktdKA0Wzb3UxlrtT9LrHt3Pp/w8/Z+8FfCm6N14f8MaPp2oMmyS/W3D304/6aXDZlk+rua7CikMBxRRRQAUMu4UUUAflZ/wVm/4JMjwkdU+KXwv0l5NIbfdeIfD1lFlrLqXvLSPo0fUyQjG37yELuUfHX7P/wC2H8TvgPO0vgvxZHeWdxFBA9hfCK7SSKIMIYUiuBvXarkAWxGM4DHiv6GGXcK/JP8A4LH/APBL6z+Fur2/xN+GenyLa+JNTFnqnhyyty+y7lWSQXFqi/wuUIaID77KUGGKj9i4N4uwuMhHJ89tKO0JSSa8lK/4S+T7n4Xx5wJicJUlnnDjlTqbzjBuLfeUbf8Ak0ba7oyfDX/BdH4leBLVW8VfC/w7MpGN8IvtJ3/9/WmGfpge1bMv/BxZfTR7LT4TaSkzdDJ4qeZc/wC6top/WvgLwd4zutIdZrC8uLRpADvtpjGWH1UjNd1p3xa8RSIFbxFrjKex1CUg/wDj1fpz8L8jqS5vYx++a/BSsfjsvFviTDx5HiJfNQb+9xue83X/AAU4+PXjPxbqHibTfBGnvqOoJ5MOpf2Be3X9lwc4igLSGCOPnkshJ5JYlmJ4b4gfF746fHaCSDxp8UHstNulxJZxaokVrdL3VrXTFMZI9JlX69TXDQas19cGaZ2mmY/M7ncx/E81qWt1+NfQ5Z4e5VhZKVOnFPuormXpKTkz4nOvFLPcZFwrVZyXZzfL/wCAxUUM8PfCPwn4bRTJDqHiOVeguz9gtB6qYYXMhI7MLhQe6dq6p9YmudNjsV8m10+B/MjsrWFLe1R8Y3iKMBN5HV8bm7k1gnV4bb70i7v7o5P5Cq8/iZmP7mPb/tN1/L/9dfZ0MFhKD5oq8u71f/A+Vj83xeYY/F+7OVo9lovn3+d2b/25bBtzMqj371Be+MJ5k8u3LQxnq2fnP49vw5965/7S1w292Z2Pcmus+E3wd8VfHXxJ/Y/g/QNS8QagMeYlrHlLcHo0shxHEpx96RlXPeqxWKo04OtXkoxWrbaSXq3ojPBZfiKtWOHw0XOctEoptt+SWrMNGxUyPmrvj7wLqvws8c6t4b1y1az1fRbl7S6hJztde4I4KkYYEcFSCOCKzBJtGc8VtQrwqQVSDTi0mmtmns16mGJwtSlUlRqxcZRbTT3TWjTXe57d+wb+zLJ+1H+0Jpuk3MDSeHdKxqWtPg7TboRiHPHzSvtTgg7S7D7hr9mIIVgiVEVUVQAqgYCj0ArwH/gnF+y1/wAMy/AC1XUrfyfFXibZqOr7h88BI/dWx7/ukOCOR5jykHBFfQFfxz4ncWf23m8vYu9GleMOz/ml/wBvPb+6on+gfgzwP/q5kMfbxtiK9p1O6092H/bqev8Aecgooor85P1wKKKKACiiigAooooAKKKKACvGv2/tKmuf2UfE2qWu1bzwibbxPbyE7fLbT7mK8Y57fJC4J9Ca9lpGUOpVhkHgg96APxi8Qfscah+2xfat4w0rwv4qm8ZeJ7hr/wA/w9oSWmgxFudkkt00EErnq0kcxcsdzGVizNx/xA/4JI/H/wCE1hHdSeCv+EhtfKEkj6DexX0kBP8AAYcrMzDv5aMv+0a/c4DFFfeZB4jZzlUFRhNVKa2jO7t6NNSXkrtLoj854m8LcizqbrVIOnUe8oaXfmrOL83ZN9Wfzs698NPF3gCVo9e8LeKNBkXqupaTcWbL9RIimqdktzq0ohgW4uJG48uMFyfwFf0Y7fr+dG36/nX3VPxwrKNp4RN+U2l93K/zPy/EfRxw0p81PHNLs6ab+/nX5H4F+Cv2XPib48aNdH+Hfje/jkO1Z49EuRb595SgjH4sK96+Fn/BG740eO5VbWLPQ/BtruBc6lqKTTFT3SO280E/7Lsn4V+vQXH/ANelry8b40ZvUXLhacKfnZyf4tL74s9jLfo8ZDRlz4yrUq+V1Ffgm/ukj4y+B/8AwRR+HPgJ4rrxfqereOL2MkmFs6dp55yp8qNjKSO4aYqe619b+C/AeifDnw/DpPh/SNN0TS7fJitLC2S3gQnkkIgAye5xzWtRX5tm/EWZZpPnx9aVTsm9F6RVor5JH69kPCmUZLDkyvDxp92l7z9ZO8n82z85f+C1v7Nv9la9ovxQ023xDqWzR9a2j/lsqk28x/3kDRkngeXEO9eaf8Eov2VW+O/xw/4SjVrfzPC/geRLlg6fu7y++9BF7hMeaw5+7GCMSV+m/wAevg5pv7QHwf8AEHg/VD5drr1m1uJtm820gw0UwGRlo5FRwM8lRWZ+y/8As76T+y58FtH8H6VILr7ChkvL0wiJ9Qun5lnZcnG48BSzbUVFyQoNfoOD8SqmH4TeTxb9vfki+1N6t37rWC7JprY/Kcw8H6OK44jn0kvq1lUlHTWsnZK3Z6Tb6tNPc9BHAooor8lP3YKKKKACiiigAooooAKKKKAP/9k=");
		// 拖放至容器里时默认的节点名称
		shape.setLabel("电子邮箱发送");
		// 模板文件名
		shape.setName("mailboxsendhtml");
		// 鼠标移动至图标上显示的名称
		shape.setTitle("电子邮箱发送");
		return shape;
	}

	@Override
	public void execute(SpiderNode node, SpiderContext context, Map<String, Object> variables) {
		int datasourceId = NumberUtils.toInt(node.getStringJsonValue(DATASOURCE_ID),0);
		String mailboxMail = node.getStringJsonValue(MAILBOX_MAIL);
		String mailboxCc = node.getStringJsonValue(MAILBOX_CC);
		String mailboxBcc = node.getStringJsonValue(MAILBOX_BCC);
		String mailboxSubject = node.getStringJsonValue(MAILBOX_SUBJECT);
		String mailboxContext = node.getStringJsonValue(MAILBOX_CONTEXT);
		List<Map<String, String>> enclosure = node.getListJsonValue(ENCLOSURE_NAME, ENCLOSURE_VALUE);
		if(datasourceId == 0){
			logger.error("请选择发信邮箱！");
		}else if (!StringUtils.isNotBlank(mailboxMail)) {
			logger.error("邮箱收件人为空！");
		} else if (!StringUtils.isNotBlank(mailboxSubject)) {
			logger.error("邮箱标题为空！");
		} else if (!StringUtils.isNotBlank(mailboxContext)) {
			logger.error("邮箱发送内容为空！");
		} else {
			JavaMailSenderImpl mailboxTemplate = MailboxUtils.createMailSender(mailService.get(datasourceId + ""));
			MimeMessage message = mailboxTemplate.createMimeMessage();
			MimeMessageHelper mailMessage;
			try {
				mailMessage = new MimeMessageHelper(message, true);
				mailMessage.setFrom(mailboxTemplate.getUsername());
				// 处理收件人变量值
				mailboxMail = engine.execute(mailboxMail, variables).toString();
				mailMessage.setTo(mailboxMail.split(","));
				logger.debug("设置收件人信息成功！");
			} catch (NullPointerException e) {
				logger.error("收件人为空！");
				return;
			} catch (MessagingException e1) {
				logger.error("收件人格式不正确", e1);
				return;
			}
			try {
				// 设置抄送人
				if (StringUtils.isNotBlank(mailboxCc)) {
					mailMessage.setCc(mailboxCc.split(","));
					logger.debug("设置抄送人信息成功！");
				}
			} catch (Exception e) {
				logger.error("抄送人格式不正确", e);
				return;
			}
			try {
				// 设置暗抄送人
				if (StringUtils.isNotBlank(mailboxBcc)) {
					mailMessage.setBcc(mailboxBcc.split(","));
					logger.debug("设置暗抄送人信息成功！");
				}
			} catch (Exception e) {
				logger.error("暗抄送人格式不正确", e);
				return;
			}
			try {
				// 处理标题变量值
				mailboxSubject = engine.execute(mailboxSubject, variables).toString();
				mailMessage.setSubject(mailboxSubject);
				mailMessage.setSentDate(new Date());// 发送时间
				logger.debug("设置发送标题成功！");
			} catch (NullPointerException e) {
				logger.error("标题为空！");
				return;
			} catch (Exception e) {
				logger.error("发送标题数据格式不正确", e);
				return;
			}
			try {
				// 处理发送内容变量值
				mailboxContext = engine.execute(mailboxContext, variables).toString();
				mailMessage.setText(mailboxContext, true);
				logger.debug("设置发送内容成功！");
			} catch (NullPointerException e) {
				logger.error("发送内容为空！");
				return;
			} catch (Exception e) {
				logger.error("发送内容数据格式不正确", e);
				return;
			}
			try {
				if (enclosure != null && enclosure.size() > 0) {
					for (Map<String, String> item : enclosure) {
						String enclosureName = item.get(ENCLOSURE_NAME);
						String enclosureValue = item.get(ENCLOSURE_VALUE);
						byte[] file = (byte[]) engine.execute(enclosureValue, variables);
						InputStreamSource inputStreamSource = new ByteArrayResource(file);
						mailMessage.addAttachment(MimeUtility.encodeWord(enclosureName), inputStreamSource);
					}
				}
			} catch (Exception e) {
				logger.error("附件格式错误:{}", e);
				return;
			}
			try {
				logger.debug("邮箱发送中.......请稍等！");
				mailboxTemplate.send(message);
				logger.debug("邮箱发送成功！");
			} catch (Exception e) {
				logger.error("邮箱发送失败:{}", e);
			}
		}
	}
}
